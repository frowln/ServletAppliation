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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/courses")
public class MyCourseServlet extends HttpServlet {
    private final CourseService courseService = CourseService.getINSTANCE();
    private final UserCourseService userCourseService = UserCourseService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        if ((user.getRole()).equals("teacher")) {
            List<Course> createdCourses = courseService.createdCourses(user.getId());
            req.setAttribute("createdCourses", createdCourses);
        } else if ((user.getRole()).equals("student")) {
            int userId = user.getId();
            List<Integer> enrolledCoursesId = userCourseService.getEnrolledCourseIdsByUserId(userId);
            List<Course> enrolledCourses = new ArrayList<>();

            // Извлекаем информацию о каждом курсе по ID
            for (Integer courseId : enrolledCoursesId) {
                Course course = courseService.findCourseById(courseId);
                if (course != null) { // Добавляем только существующие курсы
                    enrolledCourses.add(course);
                }
            }
            req.setAttribute("enrolledCourses", enrolledCourses);
        }
        req.getRequestDispatcher("/WEB-INF/views/my-course.jsp").forward(req, resp);

    }
}
