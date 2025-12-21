package com.example.studentmanagementsystem.dto.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class StudentDashboardDTO {
    private double gpa;
    private int totalCredits;
    private BigDecimal tuitionOwed;
    private List<ScheduleItem> todaySchedule;
    private List<NotificationItem> notifications;

    @Data
    public static class ScheduleItem {
        private String courseName;
        private String className;
        private String startTime;
        private String endTime;
        private String room;
    }

    @Data
    public static class NotificationItem {
        private String message;
        private String createdAt;
    }
}