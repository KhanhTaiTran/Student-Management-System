package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);

    // take list of grade of 1 class
    List<Grade> findByClassroomId(Long classId);
}
