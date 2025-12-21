package com.example.studentmanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceHistoryDTO {
    private Long attendanceId;
    private Long studentId;
    private String status;
    private String note;
}