package ru.kpfu.itis.kasimov.dao;

import lombok.Getter;
import ru.kpfu.itis.kasimov.entity.Course;
import ru.kpfu.itis.kasimov.exception.DaoException;
import ru.kpfu.itis.kasimov.jdbc.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDao implements Dao<Integer, Course> {
    @Getter
    private static final CourseDao INSTANCE = new CourseDao();

    private static final String SAVE_SQL = """
            INSERT INTO courses (name, description, teacher_id) 
            VALUES (?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE courses 
            SET name = ?, description = ?, teacher_id = ?
            WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM courses
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT c.id, c.name, c.description, c.teacher_id, 
                   COALESCE(AVG(r.rating), 0) AS average_rating
            FROM courses c
            LEFT JOIN reviews r ON c.id = r.course_id
            GROUP BY c.id
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            HAVING c.id = ?
            """;

    public static final String FIND_BY_TEACHER_ID_SQL = FIND_ALL_SQL + """
            HAVING c.teacher_id = ?
            """;
    private static final String FIND_BY_NAME_SQL = """
                SELECT c.id, c.name, c.description, c.teacher_id, 
                       COALESCE(AVG(r.rating), 0) AS average_rating
                FROM courses c
                LEFT JOIN reviews r ON c.id = r.course_id
                WHERE c.name ILIKE ?
                GROUP BY c.id
            """;


    public List<Course> findByName(String query) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            statement.setString(1, "%" + query + "%");
            List<Course> courses = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = buildCourse(resultSet);
                    courses.add(course);
                }
            }
            return courses;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }



    @Override
    public boolean update(Course course) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());
            statement.setInt(3, course.getTeacherId());
            statement.setInt(4, course.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Course> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Course> courses = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = buildCourse(resultSet);
                    courses.add(course);
                }
            }
            return courses;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Course> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(buildCourse(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Course buildCourse(ResultSet resultSet) throws SQLException {
        return new Course(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("teacher_id"),
                resultSet.getDouble("average_rating")
        );
    }

    @Override
    public Course save(Course course) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());
            statement.setInt(3, course.getTeacherId());

            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    course.setId(resultSet.getInt(1));
                }
            }
            return course;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Course> findByTeacherId(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_TEACHER_ID_SQL)) {
            statement.setInt(1, id);
            List<Course> courses = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = buildCourse(resultSet);
                    courses.add(course);
                }
            }
            return courses;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private CourseDao() {
    }
}
