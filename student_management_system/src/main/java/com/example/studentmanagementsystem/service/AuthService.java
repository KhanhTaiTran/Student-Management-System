package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.dto.request.LoginRequestDTO;
import com.example.studentmanagementsystem.dto.response.JwtAuthResponseDTO;

public interface AuthService {
    JwtAuthResponseDTO login(LoginRequestDTO loginRequestDTO);

    // request forgot password
    void forgotPassword(String email);

    // request reset pass using token provided from forgotPassword() func
    void resetPassword(String token, String newPassword);

}
