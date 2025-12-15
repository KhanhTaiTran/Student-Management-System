package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.dto.request.CreateUserRequestDTO;
import com.example.studentmanagementsystem.dto.response.UserResponseDTO;
import com.example.studentmanagementsystem.entity.Role;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO createUser(CreateUserRequestDTO request) {
        // check username exist or not
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        // create user entity
        User user = new User();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // encode pass
        user.setIsActive(true);

        // Set Role
        Role role = Role.valueOf(request.getRole().toUpperCase());
        try {
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid Role! Allowed: ADMIN, TEACHER, STUDENT");
        }

        // check student ID
        if (role == Role.STUDENT) {
            if (request.getStudentId() == null || request.getStudentId().isEmpty()) {
                throw new RuntimeException("Student ID is required for Students!");
            }

            // check unique student id
            if (userRepository.existsByStudentId(request.getStudentId())) {
                throw new RuntimeException("Student ID already exist!");
            }
            user.setStudentId(request.getStudentId());
        } else {
            // teacher and admin don't need student ID
            user.setStudentId(null);
        }

        // save to db
        User savedUser = userRepository.save(user);

        // return to response
        return mapToResponse(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(user);
    }

    // helper function
    private UserResponseDTO mapToResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.getIsActive());
    }
}
