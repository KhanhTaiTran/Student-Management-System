package com.example.studentmanagementsystem.service;

import java.util.List;

import com.example.studentmanagementsystem.dto.request.CreateUserRequestDTO;
import com.example.studentmanagementsystem.dto.response.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(CreateUserRequestDTO request);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);
}
