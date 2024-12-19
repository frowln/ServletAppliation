package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.entity.Review;
import ru.kpfu.itis.kasimov.service.ReviewService;

import java.io.IOException;

@WebServlet("/addReview")
public class AddReviewServlet extends HttpServlet {
    private final ReviewService reviewService = ReviewService.getINSTANCE();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int courseId = Integer.parseInt(req.getParameter("courseId"));
        int userId = (int) req.getSession().getAttribute("userId");
        int rating = Integer.parseInt(req.getParameter("rating"));
        String comment = req.getParameter("comment");

        Review review = new Review(0, courseId, userId, rating, comment, null, null, null);
        reviewService.addReview(review);

        resp.sendRedirect("/course?id=" + courseId);
    }
}