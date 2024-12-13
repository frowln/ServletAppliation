package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.entity.Lesson;
import ru.kpfu.itis.kasimov.service.LessonService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/editLesson")
public class EditLessonServlet extends HttpServlet {
    private final LessonService lessonService = LessonService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdParam = req.getParameter("id");

        int lessonId = Integer.parseInt(lessonIdParam);
        Optional<Lesson> optionalLesson = lessonService.findLessonById(lessonId);

        if (optionalLesson.isPresent()) {
            req.setAttribute("lesson", optionalLesson.get());
            req.getRequestDispatcher("/WEB-INF/views/editLesson.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Lesson not found.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdParam = req.getParameter("id");
        String name = req.getParameter("name");
        String body = req.getParameter("body");


        int lessonId = Integer.parseInt(lessonIdParam);
        Optional<Lesson> lesson = lessonService.findLessonById(lessonId);

        lesson.get().setName(name);
        lesson.get().setBody(body);

        boolean isUpdated = lessonService.updateLesson(lesson.get());

        if (isUpdated) {
            resp.sendRedirect("/manageCourse?id=" + lesson.get().getCourseId());
        } else {
            req.setAttribute("error", "Failed to update the lesson.");
            req.setAttribute("lesson", lesson);
            req.getRequestDispatcher("/WEB-INF/views/editLesson.jsp").forward(req, resp);
        }
    }
}
