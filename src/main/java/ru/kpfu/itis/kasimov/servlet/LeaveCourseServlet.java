package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.service.UserCourseService;

import java.io.IOException;

@WebServlet("/leaveCourse")
public class LeaveCourseServlet extends HttpServlet {
    private final UserCourseService userCourseService = UserCourseService.getINSTANCE();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int courseId = Integer.parseInt(req.getParameter("courseId"));

        userCourseService.removeUserFromCourse(userId, courseId);

        resp.sendRedirect("/course?id=" + courseId);
    }
}
