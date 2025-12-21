package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    Optional<QuizResult> findByStudentIdAndQuizId(Long studentId, Long quizId);

    List<QuizResult> findByStudentId(Long studentId);
}