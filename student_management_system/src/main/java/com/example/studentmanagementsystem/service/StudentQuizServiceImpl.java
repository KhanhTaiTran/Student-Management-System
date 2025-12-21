package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.dto.request.QuizSubmissionDTO;
import com.example.studentmanagementsystem.entity.*;
import com.example.studentmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentQuizServiceImpl implements StudentQuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizResultRepository quizResultRepository;
    private final UserRepository userRepository;

    public StudentQuizServiceImpl(QuizRepository quizRepository, QuestionRepository questionRepository,
            QuizResultRepository quizResultRepository, EnrollmentRepository enrollmentRepository,
            UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.quizResultRepository = quizResultRepository;
        this.userRepository = userRepository;
    }

    // 1. Obtain the list of student quizzes.
    public List<Quiz> getStudentQuizzes(Long studentId) {

        return quizRepository.findQuizzesByStudentId(studentId);
    }

    // 2. USE THE QUESTIONS TO SOLVE THE PROBLEM (IMPORTANT: YOU MUST HIDE THE
    // ANSWERS)
    public Quiz getQuizForTaking(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // SECURITY LOGIC: Delete correctAnswer before sending to Frontend
        // We are not modifying the DB, only this temporary Java object
        quiz.getQuestions().forEach(q -> q.setCorrectAnswer(null));

        return quiz;
    }

    // auto scoring
    @Transactional
    public QuizResult submitQuiz(Long studentId, QuizSubmissionDTO submission) {
        // Collect student information and quiz
        User student = userRepository.findById(studentId).orElseThrow();
        Quiz quiz = quizRepository.findById(submission.getQuizId()).orElseThrow();

        // Get the correct answer from the database
        List<Question> dbQuestions = questionRepository.findByQuizId(submission.getQuizId());

        // Map for quick navigation: QuestionID -> CorrectAnswer
        Map<Long, String> correctAnswersMap = dbQuestions.stream()
                .collect(Collectors.toMap(Question::getId, Question::getCorrectAnswer));

        int correctCount = 0;
        int totalQuestions = dbQuestions.size();

        // comapre
        for (QuizSubmissionDTO.AnswerDTO ans : submission.getAnswers()) {
            String correct = correctAnswersMap.get(ans.getQuestionId());
            // compare
            if (correct != null && correct.equalsIgnoreCase(ans.getSelectedOption())) {
                correctCount++;
            }
        }

        double score = 0;
        if (totalQuestions > 0) {
            score = (double) correctCount / totalQuestions * 100.0;

            score = Math.round(score * 100.0) / 100.0;
        }

        QuizResult result = new QuizResult();
        result.setStudent(student);
        result.setQuiz(quiz);
        result.setScore(score);
        result.setSubmittedAt(LocalDateTime.now());

        return quizResultRepository.save(result);
    }
}