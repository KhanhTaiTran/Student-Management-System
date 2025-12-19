package com.example.studentmanagementsystem.dto.request;

import java.time.LocalDate;

import com.example.studentmanagementsystem.entity.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequestDTO {

    private Long studentId;
    private Long classId;

    private LocalDate attendanceDate;
    private AttendanceStatus status; // PRESENT / ABSENT / LATE

    private String note;
}
