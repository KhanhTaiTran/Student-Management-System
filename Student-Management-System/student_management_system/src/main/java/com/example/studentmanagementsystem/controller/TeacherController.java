package com.example.studentmanagementsystem.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // --- FAKE DỮ LIỆU ĐỂ TEST UI ---
        // Giả vờ đăng nhập với quyền TEACHER
        session.setAttribute("role", "TEACHER");
        session.setAttribute("username", "GV. Trần Thị B");
        session.setAttribute("fullName", "Trần Thị B");

        model.addAttribute("pageTitle", "Dashboard Giáo Viên");
        return "teacher/dashboard"; // Trả về file HTML bên dưới
    }

    @GetMapping("/class-list")
    public String classList(HttpSession session, Model model) {
        model.addAttribute("pageTitle", "Danh sách lớp dạy");
        return "teacher/class-list";
    }
    
    @GetMapping("/grading")
    public String grading(HttpSession session, Model model) {
        model.addAttribute("pageTitle", "Nhập điểm - Lập trình Java Web");
        return "teacher/grading"; // Trả về file HTML nhập điểm
    }

    @GetMapping("/quiz-create")
    public String createQuiz(HttpSession session, Model model) {
        model.addAttribute("pageTitle", "Tạo bài kiểm tra trắc nghiệm");
        return "teacher/quiz-create"; // Trả về file HTML
    }
}