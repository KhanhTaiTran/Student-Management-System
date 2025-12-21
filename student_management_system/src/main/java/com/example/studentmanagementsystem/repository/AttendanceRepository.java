package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.entity.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Điểm danh theo lớp và ngày
    List<Attendance> findByClassroomIdAndAttendanceDate(
            Long classroomId,
            LocalDate attendanceDate);

    // all attendance of 1 class
    List<Attendance> findByClassroom_Id(Long classroomId);

    Optional<Attendance> findByStudentIdAndClassroomIdAndAttendanceDate(Long studentId, Long classroomId,
            LocalDate attendanceDate);
}
