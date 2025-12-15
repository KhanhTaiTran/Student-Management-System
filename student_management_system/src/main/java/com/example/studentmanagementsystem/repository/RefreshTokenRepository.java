package com.example.studentmanagementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentmanagementsystem.entity.RefreshToken;
import com.example.studentmanagementsystem.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
