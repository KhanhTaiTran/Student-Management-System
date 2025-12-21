package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.dto.request.QuizSubmissionDTO;
import com.example.studentmanagementsystem.security.CustomUserDetails;
import com.example.studentmanagementsystem.service.StudentQuizServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/quizzes")
public class StudentQuizController {

    private final StudentQuizServiceImpl studentQuizService;

    public StudentQuizController(StudentQuizServiceImpl studentQuizService) {
        this.studentQuizService = studentQuizService;
    }

    // 1. Lấy danh sách Quiz của tôi
    @GetMapping
    public ResponseEntity<?> getMyQuizzes(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(studentQuizService.getStudentQuizzes(user.getId()));
    }

    // 2. Vào làm bài (Lấy câu hỏi)
    @GetMapping("/{id}/take")
    public ResponseEntity<?> takeQuiz(@PathVariable Long id) {
        return ResponseEntity.ok(studentQuizService.getQuizForTaking(id));
    }

    // 3. Nộp bài (Chấm điểm)
    @PostMapping("/submit")
    public ResponseEntity<?> submitQuiz(@AuthenticationPrincipal CustomUserDetails user,
            @RequestBody QuizSubmissionDTO submission) {
        return ResponseEntity.ok(studentQuizService.submitQuiz(user.getId(), submission));
    }
}