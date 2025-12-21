package com.example.studentmanagementsystem.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal; // 🔥 BẮT BUỘC
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.studentmanagementsystem.dto.request.AttendanceRequestDTO;
import com.example.studentmanagementsystem.dto.response.AttendanceHistoryDTO;
import com.example.studentmanagementsystem.dto.response.ClassWithStudentCountDTO;
import com.example.studentmanagementsystem.dto.response.TeacherDashboardDTO;
import com.example.studentmanagementsystem.entity.*;
import com.example.studentmanagementsystem.repository.UserRepository;
import com.example.studentmanagementsystem.security.CustomUserDetails;
import com.example.studentmanagementsystem.service.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {

        private final ClassroomService classroomService;
        private final GradeService gradeService;
        private final AttendanceService attendanceService;
        private final QuizService quizService;
        private final TeacherService teacherService;
        private final TeacherDashboardService dashboardService;
        private final UserRepository userRepository;

        // ===================== DASHBOARD =====================
        @GetMapping("/dashboard")
        public ResponseEntity<TeacherDashboardDTO> getDashboard(
                        @AuthenticationPrincipal CustomUserDetails userDetails) {
                return ResponseEntity.ok(teacherService.getDashboardData(userDetails.getId()));
        }

        // ===================== CLASS =====================
        // Lấy danh sách lớp dạy kèm số lượng sinh viên
        @GetMapping("/classes")
        public ResponseEntity<List<ClassWithStudentCountDTO>> getTeacherClasses(
                        @AuthenticationPrincipal CustomUserDetails userDetails) {
                // userDetails.getId() chính là teacherId
                return ResponseEntity.ok(classroomService.getClassesWithStudentCount(userDetails.getId()));
        }

        // ===================== GRADE =====================

        // Xem điểm sinh viên trong 1 lớp
        @GetMapping("/grades/{classId}")
        public ResponseEntity<List<Grade>> getGradesByClass(@PathVariable Long classId) {
                return ResponseEntity.ok(
                                gradeService.getGradesByClass(classId));
        }

        // Nhập / cập nhật điểm sinh viên
        @PostMapping("/grades")
        public ResponseEntity<Grade> saveGrade(@RequestBody Grade grade) {
                return ResponseEntity.ok(
                                gradeService.saveOrUpdate(grade));
        }

        // ===================== ATTENDANCE =====================

        // Điểm danh sinh viên
        // @PostMapping("/attendance")
        // public ResponseEntity<Attendance> markAttendance(@RequestBody Attendance
        // attendance) {
        // return ResponseEntity.ok(
        // attendanceService.markAttendance(attendance));
        // }

        // Xem điểm danh theo lớp và ngày
        @GetMapping("/attendance/{classId}")
        public ResponseEntity<List<Attendance>> getAttendance(
                        @PathVariable Long classId,
                        @RequestParam LocalDate date) {
                return ResponseEntity.ok(
                                attendanceService.getAttendanceByClassAndDate(classId, date));
        }

        // ===================== QUIZ =====================

        // Giao quiz / bài tập
        @PostMapping("/quiz")
        public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
                return ResponseEntity.ok(
                                quizService.createQuiz(quiz));
        }

        // Xem danh sách quiz của lớp
        @GetMapping("/quiz/{classId}")
        public ResponseEntity<List<Quiz>> getQuizzes(@PathVariable Long classId) {
                return ResponseEntity.ok(
                                quizService.getQuizzesByClass(classId));

                // classes

        }

        @PostMapping("/attendance")
        public ResponseEntity<?> markAttendance(@RequestBody AttendanceRequestDTO request) {
                return ResponseEntity.ok(attendanceService.markAttendance(request));
        }

        @GetMapping("/attendance/history")
        public ResponseEntity<List<AttendanceHistoryDTO>> getAttendanceHistory(
                        @RequestParam Long classId,
                        @RequestParam LocalDate date) {
                // Hàm service này giờ trả về List<DTO> nên Jackson sẽ serialize ngon lành
                return ResponseEntity.ok(attendanceService.getAttendanceHistory(classId, date));
        }
}
