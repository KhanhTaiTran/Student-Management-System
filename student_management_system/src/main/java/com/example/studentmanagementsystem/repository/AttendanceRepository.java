package com.example.studentmanagementsystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentmanagementsystem.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Điểm danh theo lớp và ngày
    List<Attendance> findByClassroomIdAndAttendanceDate(
            Long classId,
            LocalDate attendanceDate);
}
