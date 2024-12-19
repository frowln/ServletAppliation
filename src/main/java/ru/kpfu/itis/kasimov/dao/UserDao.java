package ru.kpfu.itis.kasimov.dao;

import ru.kpfu.itis.kasimov.jdbc.ConnectionManager;
import lombok.Getter;
import ru.kpfu.itis.kasimov.entity.User;
import ru.kpfu.itis.kasimov.exception.DaoException;
import ru.kpfu.itis.kasimov.util.GenerateDefaultIcon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Integer, User> {
    @Getter
    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO users (name, email, password, role, avatar_url) 
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE users 
            SET name = ?, email = ?, password = ?, role = ?, avatar_url = ?
            WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, name, email, password, role, avatar_url
            FROM users
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    public static final String FIND_BY_EMAIL_SQL = FIND_ALL_SQL + """
            WHERE email = ?
            """;

    @Override
    public boolean update(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());

            if (user.getAvatarUrl() == null) {
                statement.setNull(5, Types.VARCHAR);
            } else {
                statement.setString(5, user.getAvatarUrl());
            }

            statement.setInt(6, user.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<User> users = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = buildUser(resultSet);
                    users.add(user);
                }
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(buildUser(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("role"),
                resultSet.getString("avatar_url")
        );
    }

    @Override
    public User save(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            user.setAvatarUrl(GenerateDefaultIcon.generateDefaultIcon(user.getName()));
            statement.setString(5, user.getAvatarUrl());

            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                }
            }
            return user;
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

    public Optional<User> findByEmail(String email) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_SQL)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(buildUser(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private UserDao() {
    }
}
