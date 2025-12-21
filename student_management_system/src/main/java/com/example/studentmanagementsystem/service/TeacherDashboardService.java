package com.example.studentmanagementsystem.service;

import org.springframework.stereotype.Service;

import com.example.studentmanagementsystem.repository.ClassroomRepository;
import com.example.studentmanagementsystem.repository.EnrollmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherDashboardService {

    private final ClassroomRepository classroomRepository;
    private final EnrollmentRepository enrollmentRepository;

    // total of class this teacher is teaching
    public long getTotalTeachingClasses(Long teacherId) {
        return classroomRepository.countByTeacherId(teacherId);
    }

    // total of student (duplicated)
    public long getTotalStudents(Long teacherId) {
        return enrollmentRepository.countTotalStudentsByTeacher(teacherId);
    }
}
