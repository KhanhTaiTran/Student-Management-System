package com.example.studentmanagementsystem.service;

import java.util.List;

import com.example.studentmanagementsystem.dto.request.GradeRequestDTO;
import com.example.studentmanagementsystem.dto.response.EnrollmentResponseDTO;
import com.example.studentmanagementsystem.entity.Enrollment;

public interface EnrollmentService {
    Enrollment enrollStudent(Long studenId, Long classId);

    // history score
    List<EnrollmentResponseDTO> getStudentEnrollments(Long studentId);

    // teacher get the list of student in class
    List<Enrollment> getClassEnrollments(Long classId);

    // teacher input score
    Enrollment updateGrade(Long enrollmentId, GradeRequestDTO gradeRequestDTO);

    // drop
    void dropCourse(Long studentId, Long classId);
}
