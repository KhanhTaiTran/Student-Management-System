package com.example.studentmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Sinh viên
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    // Lớp học
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Classroom classroom;

    private Double inClass;
    private Double midTerm;
    private Double finalExam;

    @Column(name = "total")
    private Double total;
}
