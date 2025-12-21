package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.dto.request.QuizSubmissionDTO;
import com.example.studentmanagementsystem.entity.*;
import com.example.studentmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentQuizServiceImpl implements StudentQuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizResultRepository quizResultRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public StudentQuizServiceImpl(QuizRepository quizRepository, QuestionRepository questionRepository,
            QuizResultRepository quizResultRepository, EnrollmentRepository enrollmentRepository,
            UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.quizResultRepository = quizResultRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    // 1. LẤY DANH SÁCH QUIZ CỦA SINH VIÊN
    public List<Quiz> getStudentQuizzes(Long studentId) {

        return quizRepository.findQuizzesByStudentId(studentId);
    }

    // 2. LẤY ĐỀ BÀI ĐỂ LÀM (QUAN TRỌNG: PHẢI GIẤU ĐÁP ÁN)
    public Quiz getQuizForTaking(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // 🔥 LOGIC BẢO MẬT: Xóa correctAnswer trước khi gửi về Frontend
        // Chúng ta không sửa vào DB, chỉ sửa trên object java tạm thời này thôi
        quiz.getQuestions().forEach(q -> q.setCorrectAnswer(null));

        return quiz;
    }

    // 3. CHẤM ĐIỂM TỰ ĐỘNG
    @Transactional
    public QuizResult submitQuiz(Long studentId, QuizSubmissionDTO submission) {
        // Lấy thông tin sinh viên và bài quiz
        User student = userRepository.findById(studentId).orElseThrow();
        Quiz quiz = quizRepository.findById(submission.getQuizId()).orElseThrow();

        // Lấy đáp án chuẩn từ Database (Không tin tưởng client)
        List<Question> dbQuestions = questionRepository.findByQuizId(submission.getQuizId());

        // Map để tra cứu nhanh: QuestionID -> CorrectAnswer
        Map<Long, String> correctAnswersMap = dbQuestions.stream()
                .collect(Collectors.toMap(Question::getId, Question::getCorrectAnswer));

        int correctCount = 0;
        int totalQuestions = dbQuestions.size();

        // So sánh đáp án
        for (QuizSubmissionDTO.AnswerDTO ans : submission.getAnswers()) {
            String correct = correctAnswersMap.get(ans.getQuestionId());
            // So sánh (bỏ qua chữ hoa thường cho chắc)
            if (correct != null && correct.equalsIgnoreCase(ans.getSelectedOption())) {
                correctCount++;
            }
        }

        // Tính điểm (Thang 10)
        double score = 0;
        if (totalQuestions > 0) {
            score = (double) correctCount / totalQuestions * 10.0;
            // Làm tròn 2 chữ số thập phân
            score = Math.round(score * 100.0) / 100.0;
        }

        // Lưu kết quả
        QuizResult result = new QuizResult();
        result.setStudent(student);
        result.setQuiz(quiz);
        result.setScore(score);
        result.setSubmittedAt(LocalDateTime.now());

        return quizResultRepository.save(result);
    }
}