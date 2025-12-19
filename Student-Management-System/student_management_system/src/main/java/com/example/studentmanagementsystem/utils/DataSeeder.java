package com.example.studentmanagementsystem.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.repository.UserRepository;
import com.example.studentmanagementsystem.entity.Role;

@Component
public class DataSeeder implements CommandLineRunner {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // check if admin exist in db
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setFullName("Admin System");
            admin.setUsername("admin");
            admin.setEmail("admin@hcmiu.edu.vn");
            // pass: admin123 (hashed)
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setIsActive(true);

            userRepository.save(admin);
            System.out.println(">>> admin / admin123");
        }
    }
}
