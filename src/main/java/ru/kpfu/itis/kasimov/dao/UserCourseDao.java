package ru.kpfu.itis.kasimov.dao;

import lombok.Getter;
import ru.kpfu.itis.kasimov.entity.Course;
import ru.kpfu.itis.kasimov.entity.UserCourse;
import ru.kpfu.itis.kasimov.entity.UserCourseKey;
import ru.kpfu.itis.kasimov.exception.DaoException;
import ru.kpfu.itis.kasimov.jdbc.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserCourseDao implements Dao<UserCourseKey, UserCourse> {
    @Getter
    private static final UserCourseDao INSTANCE = new UserCourseDao();

    private static final String SAVE_SQL = """
            INSERT INTO user_courses (user_id, course_id)
            VALUES (?, ?)
            """;

    private static final String DELETE_SQL = """
            DELETE FROM user_courses
            WHERE user_id = ? AND course_id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT user_id, course_id
            FROM user_courses
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE user_id = ? AND course_id = ?
            """;

    private static final String INSERT_INTO_SQL = """
            INSERT INTO user_courses (user_id, course_id)
            VALUES (?, ?)
            """;

    private static final String USER_IN_COURSE_SQL = """
            SELECT COUNT(*) FROM user_courses
            WHERE user_id = ? AND course_id = ?
            """;

    private static final String USERS_BY_COURSE_ID_SQL = """
            SELECT user_id FROM user_courses
            WHERE course_id = ?
            """;

    private static final String USERSCOURSES_SQL = """
            SELECT user_id, course_id
            FROM user_courses
            WHERE user_id = ?
            """;

    private static final String USERSCOURSES_INTEGER_SQL = """
            SELECT course_id 
            FROM user_courses 
            WHERE user_id = ?
            """;

    private static final String COUNT_ENROLLED_STUDENTS_SQL = """
                SELECT COUNT(*)
                FROM user_courses
                WHERE course_id = ?
            """;

    private static final String REMOVE_FROM_COURSE_SQL = """
                DELETE FROM user_courses 
                WHERE user_id = ? AND course_id = ?
            """;

    public void removeUserFromCourse(int userId, int courseId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_FROM_COURSE_SQL)) {
            statement.setInt(1, userId);
            statement.setInt(2, courseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public int getEnrolledStudentsCount(int courseId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_ENROLLED_STUDENTS_SQL)) {
            statement.setInt(1, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return 0;
    }


    @Override
    public UserCourse save(UserCourse userCourse) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL)) {
            statement.setInt(1, userCourse.getId().getUserId());
            statement.setInt(2, userCourse.getId().getCourseId());

            statement.executeUpdate();
            return userCourse;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserCourse> findById(UserCourseKey id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id.getUserId());
            statement.setInt(2, id.getCourseId());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    UserCourse userCourse = new UserCourse(new UserCourseKey(
                            resultSet.getInt("user_id"),
                            resultSet.getInt("course_id")
                    ));
                    return Optional.of(userCourse);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<UserCourse> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<UserCourse> userCourses = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UserCourse userCourse = new UserCourse(new UserCourseKey(
                            resultSet.getInt("user_id"),
                            resultSet.getInt("course_id")
                    ));
                    userCourses.add(userCourse);
                }
            }
            return userCourses;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(UserCourseKey id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id.getUserId());
            statement.setInt(2, id.getCourseId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void insertUserCourse(int user_id, int course_id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_INTO_SQL)) {
            statement.setInt(1, user_id);
            statement.setInt(2, course_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean isUserEnrolledInCourse(int userId, int courseId) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_IN_COURSE_SQL)) {
            statement.setInt(1, userId);
            statement.setInt(2, courseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public List<Integer> getEnrolledCourseIdsByUserId(int userId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(USERSCOURSES_INTEGER_SQL)) {
            statement.setInt(1, userId);

            List<Integer> enrolledCourse = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                enrolledCourse.add(resultSet.getInt("course_id"));
            }
            return enrolledCourse;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Integer> getStudentsByCourseId(int courseId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(USERS_BY_COURSE_ID_SQL)) {
            statement.setInt(1, courseId);

            List<Integer> studentIds = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                studentIds.add(resultSet.getInt("user_id")); // Исправлено: получаем user_id
            }
            return studentIds;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(UserCourse userCourse) {
        throw new UnsupportedOperationException("Update is not supported for UserCourse entity.");
    }


    private UserCourseDao() {
    }
}

