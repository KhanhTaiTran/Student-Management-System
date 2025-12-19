package com.example.studentmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentmanagementsystem.entity.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    // Lấy danh sách điểm theo lớp
    List<Grade> findByClassroomId(Long classId);
}
