package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.entity.Course;
import ru.kpfu.itis.kasimov.entity.User;
import ru.kpfu.itis.kasimov.service.CourseService;

import java.io.IOException;

@WebServlet("/addCourse")
public class AddCourseServlet extends HttpServlet {
    private final CourseService courseService = CourseService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/addCourse.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole().equals("teacher")) {
            Course course = new Course(0, name, description, user.getId());
            courseService.createCourse(course);
            resp.sendRedirect("/courses");
        } else {
            resp.sendRedirect("/home");
        }
    }
}
