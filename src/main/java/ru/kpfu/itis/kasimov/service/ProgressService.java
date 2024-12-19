package ru.kpfu.itis.kasimov.service;

import lombok.Getter;
import ru.kpfu.itis.kasimov.dao.ProgressDao;
import ru.kpfu.itis.kasimov.entity.UserProgress;

import java.util.Optional;

public class ProgressService {
    @Getter
    private static final ProgressService INSTANCE = new ProgressService();

    private final ProgressDao progressDao = ProgressDao.getINSTANCE();

    public void markLessonAsCompleted(int userId, int lessonId, int courseId) {
        progressDao.markLessonAsCompleted(userId, lessonId, courseId);


    }

    public Optional<UserProgress> getProgress(int userId, int courseId) {
        return progressDao.getUserProgress(userId, courseId);
    }

    public boolean isCompleted(int userId, int courseId) {
        return progressDao.isLessonCompleted(userId, courseId);
    }
}
