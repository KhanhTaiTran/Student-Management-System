package com.example.studentmanagementsystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherPageController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "teacher/dashboard";
    }

    @GetMapping("/classes")
    public String classList() {
        return "teacher/class-list";
    }

    @GetMapping("/grading")
    public String grading() {
        return "teacher/grading";
    }

    @GetMapping("/quiz/create")
    public String createQuiz() {
        return "teacher/quiz-create";
    }

    @GetMapping("/contact")
    public String contact() {
        return "teacher/contact";
    }
}
