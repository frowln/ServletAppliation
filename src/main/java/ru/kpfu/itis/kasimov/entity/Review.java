package ru.kpfu.itis.kasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private int id;
    private int courseId;
    private int userId;
    private int rating;
    private String comment;
    private Timestamp createdAt;
    private String userName;
    private String avatarUrl;
}
