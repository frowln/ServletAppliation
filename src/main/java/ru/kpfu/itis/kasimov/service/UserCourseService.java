package ru.kpfu.itis.kasimov.service;

import lombok.Getter;
import ru.kpfu.itis.kasimov.dao.UserCourseDao;
import ru.kpfu.itis.kasimov.dao.UserDao;
import ru.kpfu.itis.kasimov.entity.Course;
import ru.kpfu.itis.kasimov.entity.User;
import ru.kpfu.itis.kasimov.entity.UserCourse;
import ru.kpfu.itis.kasimov.entity.UserCourseKey;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserCourseService {
    private static UserCourseDao userCourseDao = UserCourseDao.getINSTANCE();
    private static UserDao userDao = UserDao.getINSTANCE();
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

    public List<User> getStudentsByCourseId(int courseId) {
        List<Integer> idsOfStudent = userCourseDao.getStudentsByCourseId(courseId);
        List<User> students = new ArrayList<>();

        for (Integer studentId : idsOfStudent) {
            User student = userDao.findById(studentId).orElse(null);
            if (student != null) {
                students.add(student);
            }
        }
        return students;
    }

    public boolean deleteUserCourse(int userId, int courseId) {
        UserCourseKey key = new UserCourseKey(userId, courseId);
        return userCourseDao.delete(key);
    }
}
