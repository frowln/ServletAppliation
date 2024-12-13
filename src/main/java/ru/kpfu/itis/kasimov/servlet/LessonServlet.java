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
public class LessonServlet extends HttpServlet {
    private final LessonService lessonService = LessonService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdParam = req.getParameter("id");

        if (lessonIdParam == null || lessonIdParam.isEmpty()) {
            resp.sendRedirect("/courses?error=invalid_lesson_id");
            return;
        }

        try {
            int lessonId = Integer.parseInt(lessonIdParam);
            Optional<Lesson> lessonOptional = lessonService.findLessonById(lessonId);

            if (lessonOptional.isEmpty()) {
                resp.sendRedirect("/courses?error=lesson_not_found");
                return;
            }

            Lesson lesson = lessonOptional.get();
            req.setAttribute("lesson", lesson);

            req.getRequestDispatcher("/WEB-INF/views/lesson.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/courses?error=invalid_lesson_id_format");
        }
    }
}