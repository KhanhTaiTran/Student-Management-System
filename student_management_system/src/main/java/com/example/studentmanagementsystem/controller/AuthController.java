package com.example.studentmanagementsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagementsystem.dto.request.ForgotPasswordRequestDTO;
import com.example.studentmanagementsystem.dto.request.LoginRequestDTO;
import com.example.studentmanagementsystem.dto.request.ResetPasswordRequestDTO;
import com.example.studentmanagementsystem.dto.response.JwtAuthResponseDTO;
import com.example.studentmanagementsystem.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // api login: http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.login(loginRequestDTO);

        JwtAuthResponseDTO jwtAuthResponseDTO = new JwtAuthResponseDTO();
        jwtAuthResponseDTO.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponseDTO);
    }

    // forgot pass
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok("Link reset Password has been send to your email!");
    }

    // reset pass
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDTO request) {
        authService.resetPassword(request.getToken(), request.getNewPassword());

        return ResponseEntity.ok("Password was changed! You can login now.");
    }

}
