package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.dto.request.LoginRequestDTO;

public interface AuthService {
    String login(LoginRequestDTO loginRequestDTO);

    // request forgot password
    void forgotPassword(String email);

    // request reset pass using token provided from forgotPassword() func
    void resetPassword(String token, String newPassword);

    // send mail helper
    // void sendEmail(String to, String subject, String content);
}
