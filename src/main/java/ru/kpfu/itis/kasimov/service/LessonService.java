package ru.kpfu.itis.kasimov.service;

import lombok.Getter;
import ru.kpfu.itis.kasimov.dao.LessonDao;
import ru.kpfu.itis.kasimov.entity.Lesson;

import java.util.List;
import java.util.Optional;

public class LessonService {
    private static LessonDao lessonDao = LessonDao.getINSTANCE();
    @Getter
    private static final LessonService INSTANCE = new LessonService();

    public List<Lesson> findLessonsByCourseId(Integer courseId) {
            return lessonDao.findByCourseId(courseId);
    }

    public Optional<Lesson> findLessonById(Integer lessonId) {
        return lessonDao.findById(lessonId);
    }
}
