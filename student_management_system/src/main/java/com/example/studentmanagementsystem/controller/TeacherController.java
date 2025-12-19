package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.entity.*;
import com.example.studentmanagementsystem.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {

    private final ClassroomService classroomService;
    private final GradeService gradeService;
    private final AttendanceService attendanceService;
    private final QuizService quizService;

    // ===================== CLASS =====================

    // Xem các lớp giáo viên đang dạy
    @GetMapping("/classes/{teacherId}")
    public ResponseEntity<List<Classroom>> getMyClasses(@PathVariable Long teacherId) {
        return ResponseEntity.ok(
                classroomService.getClassesByTeacher(teacherId));
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
    @PostMapping("/attendance")
    public ResponseEntity<Attendance> markAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(
                attendanceService.markAttendance(attendance));
    }

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
    }
}
