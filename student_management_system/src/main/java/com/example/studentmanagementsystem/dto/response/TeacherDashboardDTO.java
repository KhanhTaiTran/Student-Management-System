package com.example.studentmanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherDashboardDTO {
    private long totalClasses;
    private long totalStudents;
}
