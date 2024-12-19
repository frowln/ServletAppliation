package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.entity.Course;
import ru.kpfu.itis.kasimov.service.CourseService;

import java.io.IOException;
import java.util.List;

@WebServlet("/searchCourses")
public class SearchCoursesServlet extends HttpServlet {
    private final CourseService courseService = CourseService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");

        if (query == null || query.isBlank()) {
            resp.sendRedirect("/courses?error=empty_search");
            return;
        }

        List<Course> searchResults = courseService.searchCoursesByName(query);

        req.setAttribute("searchResults", searchResults);
        req.setAttribute("query", query);
        req.getRequestDispatcher("/WEB-INF/views/search-results.jsp").forward(req, resp);
    }
}
