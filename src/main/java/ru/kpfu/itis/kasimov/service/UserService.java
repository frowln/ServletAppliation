package ru.kpfu.itis.kasimov.service;

import lombok.Getter;
import ru.kpfu.itis.kasimov.dao.UserDao;
import ru.kpfu.itis.kasimov.entity.User;
import ru.kpfu.itis.kasimov.servlet.UploadFileServlet;

import java.util.Optional;
import java.util.logging.Logger;

public class UserService {
    private static UserDao userDao = UserDao.getINSTANCE();
    @Getter
    public static final UserService INSTANCE = new UserService();

    private UserService() {
    }

    public Optional<User> login(String email, String password) {
        Optional<User> user = userDao.findByEmail(email);

        return user.filter(u -> u.getPassword().equals(password));
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> user = userDao.findByEmail(email);
        return user;
    }


    public Optional<User> register(User user) {
        try {
            User newUser = userDao.save(user);
            return Optional.of(newUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean update(User user) {
        return userDao.update(user);
    }

    public Optional<User> findById(Integer id) {
        Optional<User> user = userDao.findById(id);

        return user;
    }


}
