package com.example.studentmanagementsystem.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.studentmanagementsystem.entity.Attendance;
import com.example.studentmanagementsystem.repository.AttendanceRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public Attendance markAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAttendanceByClassAndDate(Long classId, LocalDate date) {
        return attendanceRepository
                .findByClassroomIdAndAttendanceDate(classId, date);
    }
}
