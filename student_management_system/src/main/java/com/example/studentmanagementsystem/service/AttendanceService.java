package com.example.studentmanagementsystem.service;

import java.time.LocalDate;
import java.util.List;

import com.example.studentmanagementsystem.dto.request.AttendanceRequestDTO;
import com.example.studentmanagementsystem.dto.response.AttendanceHistoryDTO;
import com.example.studentmanagementsystem.entity.Attendance;

public interface AttendanceService {

    Attendance markAttendance(Attendance attendance);

    List<Attendance> getAttendanceByClassAndDate(Long classId, LocalDate date);

    public List<AttendanceHistoryDTO> getAttendanceHistory(Long classId, LocalDate date);

    Attendance markAttendance(AttendanceRequestDTO request);

}
