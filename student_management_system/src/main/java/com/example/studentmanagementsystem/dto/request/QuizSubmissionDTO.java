package com.example.studentmanagementsystem.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class QuizSubmissionDTO {
    private Long quizId;
    private List<AnswerDTO> answers;

    @Data
    public static class AnswerDTO {
        private Long questionId;
        private String selectedOption; // "A", "B", "C", "D"
    }
}