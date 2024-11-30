package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.entity.Course;
import ru.kpfu.itis.kasimov.entity.Lesson;
import ru.kpfu.itis.kasimov.service.CourseService;
import ru.kpfu.itis.kasimov.service.LessonService;

import java.io.IOException;
import java.util.List;

@WebServlet("/course")
public class LessonsServlet extends HttpServlet {
    private final CourseService courseService = CourseService.getINSTANCE();
    private final LessonService lessonService = LessonService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseIdParam = req.getParameter("id");

        // Проверяем, что параметр "id" не null и является числом
        if (courseIdParam == null || courseIdParam.isEmpty()) {
            resp.sendRedirect("/home?error=invalid_course");
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdParam); // Парсим ID курса
            Course course = courseService.findCourseById(courseId);

            if (course == null) {
                resp.sendRedirect("/home?error=course_not_found");
                return;
            }

            List<Lesson> lessons = lessonService.findLessonsByCourseId(courseId);

            req.setAttribute("course", course);
            req.setAttribute("lessons", lessons);
            req.getRequestDispatcher("/WEB-INF/views/course.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect("/home?error=invalid_course_id");
        }
    }
}
