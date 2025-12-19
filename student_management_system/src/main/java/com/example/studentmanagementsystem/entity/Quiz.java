package com.example.studentmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // Lớp học
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Classroom classroom;

    // Giáo viên tạo quiz
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    private Integer totalScore;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
