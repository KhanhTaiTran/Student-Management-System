package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // Lấy tất cả Quiz của một Lớp cụ thể
    List<Quiz> findByClassroomId(Long classroomId);

    // Lấy tất cả Quiz do một Giáo viên tạo (Dựa vào classroom -> teacher)
    // Cái này dùng cho trang "My Quizzes" của Teacher
    @Query("SELECT q FROM Quiz q WHERE q.classroom.teacher.id = :teacherId")
    List<Quiz> findByClassroomTeacherId(Long teacherId);
}