package com.example.studentmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Chạy trang chủ: http://localhost:8080
    @GetMapping("/")
    public String index() {
        return "index"; // Trỏ về templates/index.html
    }

    // Chạy trang login: http://localhost:8080/login
    @GetMapping("/login")
    public String login() {
        return "login"; // Trỏ về templates/login.html
    }
}