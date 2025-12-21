package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.dto.request.QuizRequestDTO;
import com.example.studentmanagementsystem.entity.Classroom;
import com.example.studentmanagementsystem.entity.Question;
import com.example.studentmanagementsystem.entity.Quiz;
import com.example.studentmanagementsystem.repository.ClassroomRepository;
import com.example.studentmanagementsystem.repository.QuestionRepository;
import com.example.studentmanagementsystem.repository.QuizRepository;
import com.example.studentmanagementsystem.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/teacher/quizzes")
public class QuizController {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final ClassroomRepository classroomRepository;

    public QuizController(QuizRepository quizRepository, QuestionRepository questionRepository,
            ClassroomRepository classroomRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.classroomRepository = classroomRepository;
    }

    // 1. TẠO QUIZ MỚI
    @PostMapping
    @Transactional
    public ResponseEntity<?> createQuiz(@RequestBody QuizRequestDTO request) {
        Classroom classroom = classroomRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setClassroom(classroom);

        Quiz savedQuiz = quizRepository.save(quiz);

        List<Question> questions = new ArrayList<>();
        for (var qDto : request.getQuestions()) {
            Question q = new Question();
            q.setQuestionText(qDto.getQuestionText());
            q.setOptionA(qDto.getOptionA());
            q.setOptionB(qDto.getOptionB());
            q.setOptionC(qDto.getOptionC());
            q.setOptionD(qDto.getOptionD());
            q.setCorrectAnswer(qDto.getCorrectAnswer());
            q.setQuiz(savedQuiz);
            questions.add(q);
        }
        questionRepository.saveAll(questions);

        return ResponseEntity.ok("Quiz created successfully!");
    }

    // 2. LẤY DANH SÁCH QUIZ
    @GetMapping
    public ResponseEntity<?> getQuizzesByTeacher(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null)
            return ResponseEntity.ok(quizRepository.findAll()); // Fallback nếu chưa login
        return ResponseEntity.ok(quizRepository.findByClassroomTeacherId(userDetails.getId()));
    }

    // 3. API LẤY CHI TIẾT (Cho Modal Edit)
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizDetail(@PathVariable Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        return ResponseEntity.ok(quiz);
    }

    // 4. API UPDATE (Fix lỗi 500 PUT Method Not Supported)
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateQuiz(@PathVariable Long id, @RequestBody QuizRequestDTO request) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Update thông tin cơ bản
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());

        // Update câu hỏi (Xóa cũ, thêm mới)
        quiz.getQuestions().clear(); // Cần orphanRemoval = true bên Entity Quiz

        List<Question> newQuestions = new ArrayList<>();
        for (var qDto : request.getQuestions()) {
            Question q = new Question();
            q.setQuestionText(qDto.getQuestionText());
            q.setOptionA(qDto.getOptionA());
            q.setOptionB(qDto.getOptionB());
            q.setOptionC(qDto.getOptionC());
            q.setOptionD(qDto.getOptionD());
            q.setCorrectAnswer(qDto.getCorrectAnswer());
            q.setQuiz(quiz);
            newQuestions.add(q);
        }

        quiz.getQuestions().addAll(newQuestions);
        quizRepository.save(quiz);

        return ResponseEntity.ok("Updated successfully!");
    }
}