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
    private Double inclass;
    private Double midTermGrade;
    private Double finalGrade;
}
