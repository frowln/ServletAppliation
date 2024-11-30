package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.entity.User;
import ru.kpfu.itis.kasimov.service.UserCourseService;

import java.io.IOException;

@WebServlet("/enroll")
public class EnrollServlet extends HttpServlet {
    private final UserCourseService userCourseService = UserCourseService.getINSTANCE();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int courseId = Integer.parseInt(req.getParameter("courseId"));

        if (!userCourseService.isUserEnrolledInCourse(userId, courseId)) {
            userCourseService.enrollUserToCourse(userId, courseId);
        }
        resp.sendRedirect("/home");
    }
}
