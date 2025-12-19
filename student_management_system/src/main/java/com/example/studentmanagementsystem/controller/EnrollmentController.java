package com.example.studentmanagementsystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.studentmanagementsystem.dto.request.EnrollmentRequestDTO;
import com.example.studentmanagementsystem.dto.request.GradeRequestDTO;
import com.example.studentmanagementsystem.dto.response.EnrollmentResponseDTO;
import com.example.studentmanagementsystem.entity.Enrollment;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.repository.UserRepository;
import com.example.studentmanagementsystem.service.EnrollmentService;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final UserRepository userRepository;

    public EnrollmentController(EnrollmentService enrollmentService, UserRepository userRepository) {
        this.enrollmentService = enrollmentService;
        this.userRepository = userRepository;
    }

    // register course
    @PostMapping("/register")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Enrollment> registerCourse(@RequestBody EnrollmentRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        User currentUser = userRepository.findByUsername(username).orElseThrow();

        return ResponseEntity.ok(enrollmentService.enrollStudent(currentUser.getId(), request.getClassId()));
    }

    // see grade
    @GetMapping("/my-courses")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<EnrollmentResponseDTO>> getMyCourses(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User currentUser = userRepository.findByUsername(username).orElseThrow();

        return ResponseEntity.ok(enrollmentService.getStudentEnrollments(currentUser.getId()));
    }

    // teacher get the list of class (to get the list of student)
    @GetMapping("/class/{classId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<List<Enrollment>> getClassList(@PathVariable Long classId) {
        return ResponseEntity.ok(enrollmentService.getClassEnrollments(classId));
    }

    // input score
    @PutMapping("/{enrollmentId}/grade")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Enrollment> updateGrade(@PathVariable Long enrollmentId,
            @RequestBody GradeRequestDTO gradeRequest) {
        return ResponseEntity.ok(enrollmentService.updateGrade(enrollmentId, gradeRequest));
    }

    // drop course
    @DeleteMapping("/drop/{classId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> dropCourse(@PathVariable Long classId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User currentUser = userRepository.findByUsername(username).orElseThrow();

        enrollmentService.dropCourse(currentUser.getId(), classId);
        return ResponseEntity.ok("Drop course successfully!");
    }
}