package com.example.studentmanagementsystem.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.studentmanagementsystem.dto.request.LoginRequestDTO;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.repository.UserRepository;
import com.example.studentmanagementsystem.security.JwtTokenProvider;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JavaMailSender mailSender;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Override
    public String login(LoginRequestDTO loginRequestDTO) {
        // validate username/password with Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

        // if validate success -> save into context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // return token
        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public void forgotPassword(String email) {
        // find user in db
        System.out.println(">>> Find: " + "[" + email + "]");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not exist!"));

        // create random token
        String token = UUID.randomUUID().toString();

        // save token into db (expiry = 5mins)
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);

        // use JavaMailSender
        String resetLink = "http://localhost:8080/reset-password?token=" + token;

        sendEmail(email, "Request to reset the password - SMS System - HCMIU",
                "Hello,\n\n" +
                        "You just send an request to reset the password. Please click enter this link:\n" +
                        resetLink +
                        "\n\nThis link will be expiry after 5 minutes.\n" +
                        "If you don't request this, please disregard this email.");
        System.out.println(">>> EMAIL SENT TO [" + email + "]");
        System.out.println(">>> RESET LINK: " + resetLink);
    }

    private void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@sms-system.hcmiu.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token).orElseThrow(() -> new RuntimeException("Invalid token!"));

        // check expiry
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("The token has expired! Please try again!");
        }

        // hash new pass and save
        user.setPassword(passwordEncoder.encode(newPassword));

        // delete token after use
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }
}
