package ru.kpfu.itis.kasimov.dao;

import lombok.Getter;
import ru.kpfu.itis.kasimov.entity.Lesson;
import ru.kpfu.itis.kasimov.exception.DaoException;
import ru.kpfu.itis.kasimov.jdbc.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LessonDao implements Dao<Integer, Lesson> {
    @Getter
    private static final LessonDao INSTANCE = new LessonDao();

    private static final String SAVE_SQL = """
            INSERT INTO lessons (name, body, course_id) 
            VALUES (?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE lessons 
            SET name = ?, body = ?, course_id = ?
            WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM lessons
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, name, body, course_id
            FROM lessons
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    public static final String FIND_BY_COURSE_ID_SQL = """
            SELECT id, name, body, course_id
            FROM lessons
            WHERE course_id = ?
            """;

    @Override
    public boolean update(Lesson lesson) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, lesson.getName());
            statement.setString(2, lesson.getBody());
            statement.setInt(3, lesson.getCourseId());
            statement.setInt(4, lesson.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Lesson> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Lesson> lessons = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Lesson lesson = buildLesson(resultSet);
                    lessons.add(lesson);
                }
            }
            return lessons;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Lesson> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(buildLesson(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Lesson buildLesson(ResultSet resultSet) throws SQLException {
        return new Lesson(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("body"),
                resultSet.getInt("course_id"));
    }

    @Override
    public Lesson save(Lesson lesson) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, lesson.getName());
            statement.setString(2, lesson.getBody());
            statement.setInt(3, lesson.getCourseId());

            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    lesson.setId(resultSet.getInt(1));
                }
            }
            return lesson;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private LessonDao() {
    }

    public List<Lesson> findByCourseId(Integer courseId) {
        try (Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_COURSE_ID_SQL)) {
            statement.setInt(1, courseId);

            List<Lesson> lessons = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Lesson lesson = buildLesson(resultSet);
                    lessons.add(lesson);
                }
            }
            return lessons;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
