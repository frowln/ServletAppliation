package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.entity.Course;
import ru.kpfu.itis.kasimov.entity.Lesson;
import ru.kpfu.itis.kasimov.entity.User;
import ru.kpfu.itis.kasimov.service.CourseService;
import ru.kpfu.itis.kasimov.service.LessonService;
import ru.kpfu.itis.kasimov.service.UserCourseService;
import ru.kpfu.itis.kasimov.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/manageCourse")
public class ManageCourseServlet extends HttpServlet {
    private final CourseService courseService = CourseService.getINSTANCE();
    private final UserCourseService userCourseService = UserCourseService.getINSTANCE();
    private final UserService userService = UserService.getINSTANCE();
    private final LessonService lessonService = LessonService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int courseId = Integer.parseInt(req.getParameter("id"));

        Course course = courseService.findCourseById(courseId);
        List<User> students = userCourseService.getStudentsByCourseId(courseId);
        Optional<User> teacher = userService.findById(course.getTeacherId());
        String name = teacher.get().getName();
        List<Lesson> lessons = lessonService.findLessonsByCourseId(courseId);

        req.setAttribute("course", course);
        req.setAttribute("students", students);
        req.setAttribute("teacherName", name);
        req.setAttribute("lessons", lessons);

        req.getRequestDispatcher("/WEB-INF/views/manageCourse.jsp").forward(req, resp);
    }
}
