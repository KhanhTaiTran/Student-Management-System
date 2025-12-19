package com.example.studentmanagementsystem.service;

import java.util.List;
import com.example.studentmanagementsystem.entity.Quiz;

public interface QuizService {

    Quiz createQuiz(Quiz quiz);

    List<Quiz> getQuizzesByClass(Long classId);
}
