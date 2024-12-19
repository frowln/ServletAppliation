package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.service.ProgressService;

import java.io.IOException;

@WebServlet("/updateLessonProgress")
public class UpdateLessonProgressServlet extends HttpServlet {
    private final ProgressService progressService = ProgressService.getINSTANCE();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        String lessonIdParam = req.getParameter("lessonId");
        String courseIdParam = req.getParameter("courseId");

        if (lessonIdParam == null || courseIdParam == null || userId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input parameters");
            return;
        }

        try {
            int lessonId = Integer.parseInt(lessonIdParam);
            int courseId = Integer.parseInt(courseIdParam);

            progressService.markLessonAsCompleted(userId, lessonId, courseId);
            resp.sendRedirect(req.getHeader("Referer"));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid lesson or course ID");
        }
    }
}
