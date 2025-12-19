package com.example.studentmanagementsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.studentmanagementsystem.entity.Quiz;
import com.example.studentmanagementsystem.repository.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> getQuizzesByClass(Long classId) {
        return quizRepository.findByClassroomId(classId);
    }
}
