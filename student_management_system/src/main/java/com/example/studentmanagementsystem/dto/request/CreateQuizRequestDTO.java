package com.example.studentmanagementsystem.dto.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuizRequestDTO {

    private Long classId;
    private String title;
    private Integer totalScore;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
