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
        // 1. Đếm số lớp giáo viên đang dạy
        long totalClasses = classroomRepository.countByTeacherId(teacherId);

        // 2. Đếm tổng số sinh viên (Dựa trên số lượt đăng ký vào các lớp của GV này)
        // Lưu ý: Cần thêm hàm countByClassRoom_Teacher_Id vào EnrollmentRepository
        long totalStudents = enrollmentRepository.countByClassRoom_Teacher_Id(teacherId);

        // 3. Đếm số bài Quiz đã tạo
        // Lưu ý: Cần thêm hàm countByClassroom_Teacher_Id vào QuizRepository
        long totalQuizzes = quizRepository.countByClassroom_Teacher_Id(teacherId);

        return new TeacherDashboardDTO(totalClasses, totalStudents, totalQuizzes);
    }
}