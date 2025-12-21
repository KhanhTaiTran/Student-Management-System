package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.dto.response.TeacherDashboardDTO;

public interface TeacherService {

    TeacherDashboardDTO getDashboardData(Long teacherId);
}