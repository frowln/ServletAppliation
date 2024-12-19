package ru.kpfu.itis.kasimov.dao;

import lombok.Getter;
import ru.kpfu.itis.kasimov.entity.Review;
import ru.kpfu.itis.kasimov.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    @Getter
    private static final ReviewDao INSTANCE = new ReviewDao();

    private static final String SAVE_SQL = """
            INSERT INTO reviews (course_id, user_id, rating, comment)
            VALUES (?, ?, ?, ?)
            """;

    private static final String FIND_BY_COURSE_SQL = """
            SELECT r.id, r.course_id, r.user_id, r.rating, r.comment, r.created_at, 
                   u.name AS user_name, u.avatar_url AS avatar_url
            FROM reviews r
            JOIN users u ON r.user_id = u.id
            WHERE r.course_id = ?
            ORDER BY r.created_at DESC
            """;

    public void saveReview(Review review) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, review.getCourseId());
            statement.setInt(2, review.getUserId());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getComment());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        review.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving review", e);
        }
    }

    public List<Review> findReviewsByCourseId(int courseId) {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_COURSE_SQL)) {
            statement.setInt(1, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String avatarUrl = resultSet.getString("avatar_url");
                    reviews.add(new Review(
                            resultSet.getInt("id"),
                            resultSet.getInt("course_id"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("rating"),
                            resultSet.getString("comment"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getString("user_name"),
                            avatarUrl
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }


    private ReviewDao() {
    }
}
