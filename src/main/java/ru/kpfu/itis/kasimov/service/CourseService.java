package ru.kpfu.itis.kasimov.service;

import lombok.Getter;
import ru.kpfu.itis.kasimov.dao.CourseDao;
import ru.kpfu.itis.kasimov.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private static CourseDao courseDao = CourseDao.getINSTANCE();
    @Getter
    public static final CourseService INSTANCE = new CourseService();

    public List<Course> findAll() {
        return courseDao.findAll();
    }

    public List<Course> createdCourses(Integer id) {
        List<Course> createdCourses = courseDao.findByTeacherId(id);
        return createdCourses;
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


}
