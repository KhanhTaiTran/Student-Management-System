package com.example.studentmanagementsystem.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class QuizRequestDTO {
    private String title;
    private String description;
    private Long classId;
    private List<QuestionRequestDTO> questions; // questions list
}