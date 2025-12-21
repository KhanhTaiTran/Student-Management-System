package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.dto.response.TeacherDashboardDTO;
import com.example.studentmanagementsystem.repository.ClassroomRepository;
import com.example.studentmanagementsystem.repository.EnrollmentRepository;
import com.example.studentmanagementsystem.repository.QuizRepository;
import com.example.studentmanagementsystem.service.TeacherService;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final ClassroomRepository classroomRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final QuizRepository quizRepository;

    // Constructor Injection (Thay vì @Autowired)
    public TeacherServiceImpl(ClassroomRepository classroomRepository,
            EnrollmentRepository enrollmentRepository,
            QuizRepository quizRepository) {
        this.classroomRepository = classroomRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.quizRepository = quizRepository;
    }

    @Override
    public TeacherDashboardDTO getDashboardData(Long teacherId) {

        long totalClasses = classroomRepository.countByTeacherId(teacherId);

        long totalStudents = enrollmentRepository.countByClassRoom_Teacher_Id(teacherId);

        long totalQuizzes = quizRepository.countByClassroom_Teacher_Id(teacherId);

        return new TeacherDashboardDTO(totalClasses, totalStudents, totalQuizzes);
    }
}