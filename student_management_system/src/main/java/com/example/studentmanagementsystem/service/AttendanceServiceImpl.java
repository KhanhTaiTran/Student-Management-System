package com.example.studentmanagementsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.studentmanagementsystem.dto.request.AttendanceRequestDTO;
import com.example.studentmanagementsystem.dto.response.AttendanceHistoryDTO;
import com.example.studentmanagementsystem.entity.Attendance;
import com.example.studentmanagementsystem.entity.Classroom;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.repository.AttendanceRepository;
import com.example.studentmanagementsystem.repository.ClassroomRepository;
import com.example.studentmanagementsystem.repository.UserRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, UserRepository userRepository,
            ClassroomRepository classroomRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
    }

    @Override
    public Attendance markAttendance(Attendance attendance) {

        return attendanceRepository.save(attendance);
    }

    @Override
    public List<AttendanceHistoryDTO> getAttendanceHistory(Long classId, LocalDate date) {
        List<Attendance> list = attendanceRepository.findByClassroomIdAndAttendanceDate(classId, date);

        // Convert Entity -> DTO
        return list.stream().map(att -> new AttendanceHistoryDTO(
                att.getId(),
                att.getStudent().getId(), // get student ID
                att.getStatus().toString(),
                att.getNote())).collect(Collectors.toList());
    }

    @Override
    public List<Attendance> getAttendanceByClassAndDate(Long classId, LocalDate date) {
        return attendanceRepository
                .findByClassroomIdAndAttendanceDate(classId, date);
    }

    @Override
    public Attendance markAttendance(AttendanceRequestDTO request) {

        if (request.getStudentId() == null) {
            throw new RuntimeException("Error: Student ID is missing (null)!");
        }

        Optional<Attendance> existingAtt = attendanceRepository.findByStudentIdAndClassroomIdAndAttendanceDate(
                request.getStudentId(),
                request.getClassId(),
                request.getAttendanceDate());

        Attendance attendance;

        if (existingAtt.isPresent()) {

            attendance = existingAtt.get();
        } else {

            attendance = new Attendance();

            // find Student & Class
            User student = userRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found ID: " + request.getStudentId()));
            Classroom classroom = classroomRepository.findById(request.getClassId())
                    .orElseThrow(() -> new RuntimeException("Classroom not found ID: " + request.getClassId()));

            attendance.setStudent(student);
            attendance.setClassroom(classroom);
            attendance.setAttendanceDate(request.getAttendanceDate());
        }
        attendance.setStatus(request.getStatus()); // PRESENT / ABSENT
        attendance.setNote(request.getNote());
        attendance.setPresent("PRESENT".equalsIgnoreCase(String.valueOf(request.getStatus())));
        System.out.println("DEBUG SAVE: Student=" + attendance.getStudent().getId());
        if (attendance.getClassroom() == null) {
            throw new RuntimeException("❌ CHẾT RỒI: Classroom bị NULL! Kiểm tra lại ClassId gửi lên từ Frontend.");
        } else {
            System.out.println("DEBUG SAVE: Classroom=" + attendance.getClassroom().getId());
        }
        return attendanceRepository.save(attendance);
    }

}
