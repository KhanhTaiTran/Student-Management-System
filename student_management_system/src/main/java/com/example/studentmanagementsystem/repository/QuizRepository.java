package com.example.studentmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentmanagementsystem.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // Lấy danh sách quiz theo lớp
    List<Quiz> findByClassroomId(Long classId);
}
