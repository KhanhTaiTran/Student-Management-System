package com.example.studentmanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponseDTO {
    private String courseName;
    private String courseCode;
    private String className;
    private String room;
    private String teacherName;
    private Integer dayOfWeek; // 2-8 (mon -sun)
    private Integer startPeriod; // 1-12
    private Integer totalPeriods; // e.g., 3
}