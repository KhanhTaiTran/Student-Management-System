package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Lấy danh sách câu hỏi của một bài Quiz
    List<Question> findByQuizId(Long quizId);
}