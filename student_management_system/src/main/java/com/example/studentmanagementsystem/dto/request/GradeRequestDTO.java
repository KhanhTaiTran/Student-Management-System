package com.example.studentmanagementsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeRequestDTO {

    private Double inclass;
    private Double midTermGrade;
    private Double finalGrade;
}
