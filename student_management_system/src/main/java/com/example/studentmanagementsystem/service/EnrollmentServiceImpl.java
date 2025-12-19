package com.example.studentmanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.studentmanagementsystem.dto.request.GradeRequestDTO;
import com.example.studentmanagementsystem.dto.response.EnrollmentResponseDTO;
import com.example.studentmanagementsystem.entity.Classroom;
import com.example.studentmanagementsystem.entity.Enrollment;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.repository.ClassroomRepository;
import com.example.studentmanagementsystem.repository.EnrollmentRepository;
import com.example.studentmanagementsystem.repository.UserRepository;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private EnrollmentRepository enrollmentRepository;
    private UserRepository userRepository;
    private ClassroomRepository classroomRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, UserRepository userRepository,
            ClassroomRepository classroomRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
    }

    @Override
    public Enrollment enrollStudent(Long studentId, Long classId) {
        // 1. Check Student
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 2. Check Class
        Classroom classroom = classroomRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        // 3. Check Duplicate
        if (enrollmentRepository.existsByStudentIdAndClassRoomId(studentId, classId)) {
            throw new RuntimeException("Student already enrolled in this class!");
        }

        // 4. Save
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setClassRoom(classroom);
        // score default is null or 0

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public List<EnrollmentResponseDTO> getStudentEnrollments(Long studentId) {
        List<Enrollment> list = enrollmentRepository.findByStudentId(studentId);
        // Dùng Java Stream để convert list Entity -> list DTO
        return list.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<Enrollment> getClassEnrollments(Long classId) {
        return enrollmentRepository.findByClassRoomId(classId);
    }

    @Override
    public Enrollment updateGrade(Long enrollmentId, GradeRequestDTO request) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (request.getInclass() != null)
            enrollment.setInclass(request.getInclass());
        if (request.getMidTermGrade() != null)
            enrollment.setMidTermGrade(request.getMidTermGrade());
        if (request.getFinalGrade() != null)
            enrollment.setFinalGrade(request.getFinalGrade());

        Double inClass = enrollment.getInclass() != null ? enrollment.getInclass() : 0.0;
        Double midTerm = enrollment.getMidTermGrade() != null ? enrollment.getMidTermGrade() : 0.0;
        Double finalG = enrollment.getFinalGrade() != null ? enrollment.getFinalGrade() : 0.0;

        double total = (inClass * 0.3) + (midTerm * 0.3) + (finalG * 0.4);

        total = Math.round(total * 100.0) / 100.0;

        enrollment.setTotalGrade(total);

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void dropCourse(Long studentId, Long classId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndClassRoomId(studentId, classId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa đăng ký lớp này!"));

        // (Optional) Kiểm tra xem có điểm chưa? Nếu có điểm rồi thì không cho hủy
        if (enrollment.getTotalGrade() != null) {
            throw new RuntimeException("Không thể hủy môn đã có điểm tổng kết!");
        }

        enrollmentRepository.delete(enrollment);
    }

    private EnrollmentResponseDTO mapToResponse(Enrollment enrollment) {
        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
        dto.setId(enrollment.getId());
        dto.setClassId(enrollment.getClassRoom().getId());
        dto.setStudentName(enrollment.getStudent().getFullName());
        dto.setStudentId(enrollment.getStudent().getStudentId());
        dto.setClassName(enrollment.getClassRoom().getClassName());
        dto.setCourseName(enrollment.getClassRoom().getCourse().getCourseName());

        Double inclass = enrollment.getInclass();
        Double mid = enrollment.getMidTermGrade();
        Double finalG = enrollment.getFinalGrade();

        dto.setInclass(inclass);
        dto.setMidTermGrade(mid);
        dto.setFinalGrade(finalG);

        if (inclass != null && mid != null && finalG != null) {
            double total = inclass * 0.3 + mid * 0.3 + finalG * 0.4;

            total = Math.round(total * 100.0) / 100.0;
            dto.setTotalGrade(total);

            dto.setStatus(total >= 50.0 ? "PASSED" : "FAILED");
        } else {
            dto.setTotalGrade(0.0);
            dto.setStatus("IN_PROGRESS");
        }

        return dto;
    }
}
