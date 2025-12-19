package com.example.studentmanagementsystem.service;

import java.util.List;

import com.example.studentmanagementsystem.dto.request.CreateUserRequestDTO;
import com.example.studentmanagementsystem.dto.response.ScheduleResponseDTO;
import com.example.studentmanagementsystem.dto.response.StudentDashboardDTO;
import com.example.studentmanagementsystem.dto.response.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(CreateUserRequestDTO request);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    void deleteUser(Long userId);

    StudentDashboardDTO getStudentDashboardInfo(Long studentId);

    com.example.studentmanagementsystem.entity.User getUserEntityById(Long id);

    List<ScheduleResponseDTO> getStudentSchedule(Long studentId);
}
