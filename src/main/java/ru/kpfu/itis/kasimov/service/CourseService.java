package ru.kpfu.itis.kasimov.service;

import lombok.Getter;
import ru.kpfu.itis.kasimov.dao.CourseDao;
import ru.kpfu.itis.kasimov.entity.Course;

import java.util.List;

public class CourseService {
    private static final CourseDao courseDao = CourseDao.getINSTANCE();
    @Getter
    public static final CourseService INSTANCE = new CourseService();

    public List<Course> findAll() {
        return courseDao.findAll();
    }

    public List<Course> createdCourses(Integer teacherId) {
        return courseDao.findByTeacherId(teacherId);
    }

    public Course createCourse(Course course) {
        return courseDao.save(course);
    }

    public Course findCourseById(Integer id) {
        return courseDao.findById(id).orElse(null);
    }

    public boolean deleteCourseById(Integer id) {
        return courseDao.delete(id);
    }

    public boolean updateCourse(Course course) {
        return courseDao.update(course);
    }

    public List<Course> searchCoursesByName(String query) {
        return courseDao.findByName(query);
    }
}