package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.dto.request.LoginRequestDTO;

public interface AuthService {
    String login(LoginRequestDTO loginRequestDTO);
}
