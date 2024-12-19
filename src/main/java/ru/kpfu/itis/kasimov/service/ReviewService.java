package ru.kpfu.itis.kasimov.service;

import lombok.Getter;
import ru.kpfu.itis.kasimov.dao.ReviewDao;
import ru.kpfu.itis.kasimov.entity.Review;

import java.util.List;

public class ReviewService {
    @Getter
    private static final ReviewService INSTANCE = new ReviewService();
    private final ReviewDao reviewDao = ReviewDao.getINSTANCE();

    public void addReview(Review review) {
        reviewDao.saveReview(review);
    }

    public List<Review> getReviewsByCourseId(int courseId) {
        return reviewDao.findReviewsByCourseId(courseId);
    }
}
