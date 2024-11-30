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

@WebServlet("/lesson")
public class LessonDetailsServlet extends HttpServlet {
    private final LessonService lessonService = LessonService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdParam = req.getParameter("id");

        try {
            int lessonId = Integer.parseInt(lessonIdParam);
            Optional<Lesson> lesson = lessonService.findLessonById(lessonId);

            req.setAttribute("lesson", lesson.get());
            req.getRequestDispatcher("/WEB-INF/views/lesson.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect("/home?error=invalid_lesson");
        }
    }
}