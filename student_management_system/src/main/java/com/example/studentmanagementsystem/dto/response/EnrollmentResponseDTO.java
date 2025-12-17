package com.example.studentmanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponseDTO {
    private Long id;
    private String studentName;
    private String studentId; // student ID (ITITIU...)
    private String className;
    private String courseName;

    // grade
    private Double inclass;
    private Double midTermGrade;
    private Double finalGrade;
    private Double totalGrade; // this field have to caculate
    private String status; // pass/fail
}
