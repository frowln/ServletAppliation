package ru.kpfu.itis.kasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int id;
    private String name;
    private String description;
    private int teacherId;
    private double averageRating;
}
