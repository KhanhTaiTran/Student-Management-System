package com.example.studentmanagementsystem.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // --- ĐOẠN NÀY LÀ FAKE DỮ LIỆU ĐỂ TEST UI ---
        // Giả vờ như đã đăng nhập là Student
        session.setAttribute("role", "STUDENT"); 
        session.setAttribute("username", "Nguyễn Văn A");
        session.setAttribute("fullName", "Sinh Viên Test");
        
        model.addAttribute("pageTitle", "Dashboard Sinh Viên");
        return "student/dashboard"; // Trả về file HTML
    }
    
@GetMapping("/schedule")
    public String schedule(HttpSession session, Model model) {
        // Fake session nếu lỡ bị mất (để test cho mượt)
        if (session.getAttribute("role") == null) {
            session.setAttribute("role", "STUDENT");
            session.setAttribute("username", "Nguyễn Văn A");
        }

        model.addAttribute("pageTitle", "Lịch học tuần này");
        return "student/schedule"; // Trả về file HTML bên dưới
    }

    @GetMapping("/grades")
    public String grades(HttpSession session, Model model) {
        model.addAttribute("pageTitle", "Kết quả học tập");
        return "student/grades"; // Trả về file HTML bảng điểm
    }

    @GetMapping("/payment")
    public String payment(HttpSession session, Model model) {
        model.addAttribute("pageTitle", "Học phí & Thanh toán");
        return "student/payment"; // Trả về file HTML học phí
    }

    @GetMapping("/course-registration")
    public String courseRegistration(HttpSession session, Model model) {
        model.addAttribute("pageTitle", "Đăng ký tín chỉ");
        return "student/course-registration"; // Trả về file HTML đăng ký
    }
}