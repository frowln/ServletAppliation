package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.service.CourseService;
import ru.kpfu.itis.kasimov.service.LessonService;

import java.io.IOException;

@WebServlet("/deleteLesson")
public class DeleteLessonServlet extends HttpServlet {
    private final LessonService lessonService = LessonService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdParam = req.getParameter("id");
        String courseIdParam = req.getParameter("courseId");

        int lessonId = Integer.parseInt(lessonIdParam);
        int courseId = Integer.parseInt(courseIdParam);

        boolean isDeleted = lessonService.deleteLesson(lessonId);

        if (isDeleted) {
            resp.sendRedirect("/manageCourse?id=" + courseId);
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete lesson.");
        }

    }
}
