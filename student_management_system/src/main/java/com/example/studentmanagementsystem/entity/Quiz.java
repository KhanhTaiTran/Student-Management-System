package com.example.studentmanagementsystem.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false)
    private String title;

    private String description;

    // @ManyToOne
    // @JoinColumn(name = "course_id")
    // private Course course;

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
