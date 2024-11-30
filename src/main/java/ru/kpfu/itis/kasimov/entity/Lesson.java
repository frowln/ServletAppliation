package ru.kpfu.itis.kasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    private int id;
    private String name;
    private String body;
    private int courseId;
}
