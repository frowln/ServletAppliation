package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.entity.Lesson;
import ru.kpfu.itis.kasimov.service.LessonService;

import java.io.IOException;

@WebServlet("/addLesson")
public class AddLessonServlet extends HttpServlet {
    private final LessonService lessonService = LessonService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseIdParam = req.getParameter("courseId");

        req.setAttribute("courseId", courseIdParam);
        req.getRequestDispatcher("/WEB-INF/views/addLesson.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String courseIdParam = req.getParameter("courseId");
        String name = req.getParameter("name");
        String body = req.getParameter("body");


        int courseId = Integer.parseInt(courseIdParam);

        Lesson lesson = new Lesson();
        lesson.setName(name);
        lesson.setBody(body);
        lesson.setCourseId(courseId);

        lessonService.saveLesson(lesson);

        resp.sendRedirect("/manageCourse?id=" + courseId);
    }
}
