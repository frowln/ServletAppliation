package ru.kpfu.itis.kasimov.dao;

import lombok.Getter;
import ru.kpfu.itis.kasimov.entity.UserProgress;
import ru.kpfu.itis.kasimov.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProgressDao {
    @Getter
    private static final ProgressDao INSTANCE = new ProgressDao();

    private static final String MARK_LESSON_COMPLETED_SQL = """
                INSERT INTO user_lessons (user_id, lesson_id, completed, last_updated)
                VALUES (?, ?, TRUE, NOW())
                ON CONFLICT (user_id, lesson_id)
                DO UPDATE SET completed = TRUE, last_updated = NOW()
            """;

    private static final String RECALCULATE_COURSE_PROGRESS_SQL = """
                INSERT INTO user_progress (user_id, course_id, completed_lessons, total_lessons, last_updated)
                VALUES (?, ?, 
                    (SELECT COUNT(*) FROM user_lessons ul JOIN lessons l ON ul.lesson_id = l.id WHERE ul.user_id = ? AND l.course_id = ? AND ul.completed = TRUE),
                    (SELECT COUNT(*) FROM lessons WHERE course_id = ?),
                    NOW())
                ON CONFLICT (user_id, course_id)
                DO UPDATE SET 
                    completed_lessons = EXCLUDED.completed_lessons,
                    total_lessons = EXCLUDED.total_lessons,
                    last_updated = NOW()
            """;

    private static final String GET_USER_PROGRESS_SQL = """
                SELECT completed_lessons, total_lessons
                FROM user_progress
                WHERE user_id = ? AND course_id = ?
            """;

    public boolean isLessonCompleted(int userId, int lessonId) {
        String sql = """
                    SELECT completed 
                    FROM user_lessons 
                    WHERE user_id = ? AND lesson_id = ?;
                """;

        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, lessonId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("completed");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check lesson completion", e);
        }

        return false;
    }


    public void markLessonAsCompleted(int userId, int lessonId, int courseId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(MARK_LESSON_COMPLETED_SQL)) {
            statement.setInt(1, userId);
            statement.setInt(2, lessonId);
            statement.executeUpdate();

            System.out.println("Lesson marked as completed: userId=" + userId + ", lessonId=" + lessonId);
            recalculateCourseProgress(userId, courseId);
            System.out.println("Course progress recalculated: userId=" + userId + ", courseId=" + courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Error marking lesson as completed", e);
        }
    }


    public void recalculateCourseProgress(int userId, int courseId) {
        String insertOrUpdateSQL = """
                    INSERT INTO user_progress (user_id, course_id, completed_lessons, total_lessons, last_updated)
                    VALUES (?, ?, 
                        (SELECT COUNT(*) 
                         FROM user_lessons ul 
                         JOIN lessons l ON ul.lesson_id = l.id 
                         WHERE ul.user_id = ? AND l.course_id = ? AND ul.completed = TRUE),
                        (SELECT COUNT(*) FROM lessons WHERE course_id = ?), 
                        NOW())
                    ON CONFLICT (user_id, course_id) 
                    DO UPDATE SET 
                        completed_lessons = EXCLUDED.completed_lessons,
                        total_lessons = EXCLUDED.total_lessons,
                        last_updated = NOW();
                """;

        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(insertOrUpdateSQL)) {
            statement.setInt(1, userId);
            statement.setInt(2, courseId);
            statement.setInt(3, userId);
            statement.setInt(4, courseId);
            statement.setInt(5, courseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to recalculate course progress", e);
        }
    }


    public Optional<UserProgress> getUserProgress(int userId, int courseId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(GET_USER_PROGRESS_SQL)) {
            statement.setInt(1, userId);
            statement.setInt(2, courseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new UserProgress(
                            userId,
                            courseId,
                            resultSet.getInt("completed_lessons"),
                            resultSet.getInt("total_lessons")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching user progress", e);
        }
        return Optional.empty();
    }

}
