package ru.kpfu.itis.kasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProgress {
    private int userId;
    private int courseId;
    private int completedLessons;
    private int totalLessons;
}
