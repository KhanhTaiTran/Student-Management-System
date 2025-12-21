package com.example.studentmanagementsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequestDTO {
    private Long classId; // student send the ID of class want register
    private Long studentId; // (Optional) Admin can register for student
}
