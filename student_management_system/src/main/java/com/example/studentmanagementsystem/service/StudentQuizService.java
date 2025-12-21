package com.example.studentmanagementsystem.service;

import java.util.List;

import com.example.studentmanagementsystem.dto.request.QuizSubmissionDTO;
import com.example.studentmanagementsystem.entity.Quiz;
import com.example.studentmanagementsystem.entity.QuizResult;

public interface StudentQuizService {

    public List<Quiz> getStudentQuizzes(Long studentId);

    public Quiz getQuizForTaking(Long quizId);

    public QuizResult submitQuiz(Long studentId, QuizSubmissionDTO submission);
}
