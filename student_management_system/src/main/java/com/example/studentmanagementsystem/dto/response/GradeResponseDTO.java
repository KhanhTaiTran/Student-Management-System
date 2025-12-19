package com.example.studentmanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeResponseDTO {

    private Long studentId;
    private String studentName;

    private Double inclass;
    private Double midTermGrade;
    private Double finalGrade;
    private Double total;
}
