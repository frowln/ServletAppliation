package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.entity.*;
import ru.kpfu.itis.kasimov.service.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/course")
public class LessonsServlet extends HttpServlet {
    private final CourseService courseService = CourseService.getINSTANCE();
    private final LessonService lessonService = LessonService.getINSTANCE();
    private final ReviewService reviewService = ReviewService.getINSTANCE();
    private final UserCourseService userCourseService = UserCourseService.getINSTANCE();
    private final ProgressService progressService = ProgressService.getINSTANCE();
    private final UserService userService = UserService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int courseId = Integer.parseInt(req.getParameter("id"));
        User user = (User) req.getSession().getAttribute("user");
        Integer userId = (Integer) req.getSession().getAttribute("userId");

        Course course = courseService.findCourseById(courseId);
        List<Lesson> lessons = lessonService.findLessonsByCourseId(courseId);
        List<Review> reviews = reviewService.getReviewsByCourseId(courseId);

        Optional<User> teacherOptional = userService.findById(course.getTeacherId());
        User teacher = teacherOptional.orElse(null);

        boolean enrolled = false;
        Optional<UserProgress> progressOpt = Optional.empty();

        if (userId != null) {
            enrolled = userCourseService.isUserEnrolledInCourse(userId, courseId);
            progressOpt = progressService.getProgress(userId, courseId);
        }
        progressOpt.ifPresent(progress -> req.setAttribute("progress", progress));
        int enrolledStudents = userCourseService.getEnrolledStudentsCount(courseId);
        req.setAttribute("course", course);
        req.setAttribute("lessons", lessons);
        req.setAttribute("reviews", reviews);
        req.setAttribute("user", user);
        req.setAttribute("enrolled", enrolled);
        req.setAttribute("teacher", teacher);
        req.setAttribute("enrolledStudents", enrolledStudents);

        req.getRequestDispatcher("/WEB-INF/views/course.jsp").forward(req, resp);
    }

}


