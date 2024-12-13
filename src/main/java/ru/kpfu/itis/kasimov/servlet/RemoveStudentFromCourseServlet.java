package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.service.UserCourseService;

import java.io.IOException;

@WebServlet("/removeStudentFromCourse")
public class RemoveStudentFromCourseServlet extends HttpServlet {
    private final UserCourseService userCourseService = UserCourseService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int courseId = Integer.parseInt(req.getParameter("courseId"));
        int studentId = Integer.parseInt(req.getParameter("studentId"));

        boolean isDeleted = userCourseService.deleteUserCourse(studentId, courseId);
        if (isDeleted) {
            resp.sendRedirect("/manageCourse?id=" + courseId);
        }
    }
}
