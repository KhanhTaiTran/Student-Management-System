package com.example.studentmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentmanagementsystem.entity.Classroom;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    // find class using classname (vd: )
    Optional<Classroom> findByClassName(String className);

    Boolean existsByClassName(String className);

    // find all classroom of 1 sepecific course
    List<Classroom> findByCourseId(Long courseId);

    // find all classrooms of 1 teacher are teaching
    List<Classroom> findByTeacherId(Long teacherId);
}
