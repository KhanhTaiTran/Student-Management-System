package com.example.studentmanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassWithStudentCountDTO {

    private Long classId;
    private String className;
    private String courseName;
    private String semester;
    private String room;
    private Long studentCount;
}
