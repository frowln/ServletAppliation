package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.kpfu.itis.kasimov.entity.UserProgress;
import ru.kpfu.itis.kasimov.service.ProgressService;

import java.io.IOException;

@WebServlet("/courseProgress")
public class CourseProgressServlet extends HttpServlet {
    private final ProgressService progressService = ProgressService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("userId");
        int courseId = Integer.parseInt(req.getParameter("courseId"));

        UserProgress progress = progressService.getProgress(userId, courseId).orElseThrow();
        req.setAttribute("progress", progress);
        req.getRequestDispatcher("/WEB-INF/views/progress.jsp").forward(req, resp);
    }
}
