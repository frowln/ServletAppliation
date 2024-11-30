package ru.kpfu.itis.kasimov.service;

import lombok.Getter;
import ru.kpfu.itis.kasimov.dao.UserCourseDao;
import ru.kpfu.itis.kasimov.entity.Course;
import ru.kpfu.itis.kasimov.entity.UserCourse;

import java.sql.SQLException;
import java.util.List;

public class UserCourseService {
    private static UserCourseDao userCourseDao = UserCourseDao.getINSTANCE();
    @Getter
    public static final UserCourseService INSTANCE = new UserCourseService();

    public void enrollUserToCourse(int userId, int courseId) {
        userCourseDao.insertUserCourse(userId, courseId);
    }

    public boolean isUserEnrolledInCourse(int userId, int courseId) {
        try {
            return userCourseDao.isUserEnrolledInCourse(userId, courseId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при проверке записи пользователя на курс", e);
        }
    }


    public List<Integer> getEnrolledCourseIdsByUserId(int userId) {
        return userCourseDao.getEnrolledCourseIdsByUserId(userId);
    }
}
