package com.example.studentmanagementsystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherViewController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "teacher/dashboard";
    }

    @GetMapping("/classes")
    public String classes() {
        return "teacher/classes";
    }

    @GetMapping("/grades/{classId}")
    public String grades() {
        return "teacher/grades";
    }

    @GetMapping("/teacher/attendance")
    public String attendanceDefaultPage() {

        return "redirect:/teacher/classes";
    }

    @GetMapping("/attendance/{classId}")
    public String attendancePage(@PathVariable Long classId) {
        return "teacher/attendance";
    }

    @GetMapping("/quizzes/{classId}")
    public String quizzes() {
        return "teacher/quizzes";
    }

    @GetMapping("/quizzes")
    public String teacherQuizzes() {
        return "teacher/quizzes";
    }

    @GetMapping("/quiz/create")
    public String createQuizPage() {
        return "teacher/create-quiz";
    }
}
