package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.entity.Course;
import ru.kpfu.itis.kasimov.entity.User;
import ru.kpfu.itis.kasimov.service.CourseService;
import ru.kpfu.itis.kasimov.service.UserCourseService;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final CourseService courseService = CourseService.getINSTANCE();
    private final UserCourseService userCourseService = UserCourseService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        List<Course> courses = courseService.findAll();
        if (user != null) {
            List<Integer> enrolledCourseIds = userCourseService.getEnrolledCourseIdsByUserId(user.getId());
            System.out.println(enrolledCourseIds);
            req.setAttribute("enrolledCourseIds", enrolledCourseIds);
        }

        req.setAttribute("courses", courses);
        req.setAttribute("isTeacher", user != null && "teacher".equals(user.getRole()));
        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
    }
}
