package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.entity.Course;
import ru.kpfu.itis.kasimov.service.CourseService;

import java.io.IOException;

@WebServlet("/editCourse")
public class EditCourseServlet extends HttpServlet {
    private final CourseService courseService = CourseService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseIdParam = req.getParameter("id");

        int courseId = Integer.parseInt(courseIdParam);
        Course course = courseService.findCourseById(courseId);

        req.setAttribute("course", course);
        req.getRequestDispatcher("/WEB-INF/views/editCourse.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseIdParam = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        int courseId = Integer.parseInt(courseIdParam);
        Course course = courseService.findCourseById(courseId);

        course.setName(name);
        course.setDescription(description);

        boolean isUpdated = courseService.updateCourse(course);

        if (isUpdated) {
            resp.sendRedirect("/manageCourse?id=" + courseId);
        } else {
            req.setAttribute("error", "Failed to update the course.");
            req.setAttribute("course", course);
            req.getRequestDispatcher("/WEB-INF/views/editCourse.jsp").forward(req, resp);
        }

    }
}
