package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.service.CourseService;

import java.io.IOException;

@WebServlet("/deleteCourse")
public class DeleteCourseServlet extends HttpServlet {
    private final CourseService courseService = CourseService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int courseId = Integer.parseInt(req.getParameter("id"));

        boolean isDeleted = courseService.deleteCourseById(courseId);
        if (isDeleted) {
            resp.sendRedirect("/courses");
        }
    }
}
