package com.example.studentmanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "classrooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String className;

    // 1 course has many class
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // 1 teacher can teach many course
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    private String semester;

    private String classRoom;// e.g. A1.202
}
