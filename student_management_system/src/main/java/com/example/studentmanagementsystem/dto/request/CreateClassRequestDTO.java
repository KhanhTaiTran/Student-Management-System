package com.example.studentmanagementsystem.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClassRequestDTO {
    private String className;
    private Long courseId;
    private Long teacherId;
    private String semester;
    private String classRoom; // e.g. A1.202
    private Integer dayOfWeek;
    private Integer startPeriod;
    private Integer totalPeriods;
    private LocalDate startDate;
    private LocalDate endDate;
}
