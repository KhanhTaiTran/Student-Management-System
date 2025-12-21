package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.security.CustomUserDetails;
import com.example.studentmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentViewController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.getUserEntityById(userDetails.getId());
        model.addAttribute("user", user);
        return "student/dashboard";
    }

    @GetMapping("/schedule")
    public String schedule() {
        return "student/schedule";
    }

    @GetMapping("/grades")
    public String grades() {
        return "student/grades";
    }

    @GetMapping("/payment")
    public String payment() {
        return "student/payment";
    }

    @GetMapping("/course-registration")
    public String courseRegistration() {
        return "student/course-registration";
    }

    @GetMapping("/quizzes")
    public String studentQuizzesPage() {
        return "student/quizzes";
    }

    @GetMapping("/quiz/take/{id}")
    public String takeQuizPage(@PathVariable Long id, Model model) {
        model.addAttribute("quizId", id);
        return "student/take-quiz";
    }
}