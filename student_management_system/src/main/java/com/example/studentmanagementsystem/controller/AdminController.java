package com.example.studentmanagementsystem.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagementsystem.dto.request.CreateUserRequestDTO;
import com.example.studentmanagementsystem.dto.response.UserResponseDTO;
import com.example.studentmanagementsystem.entity.Classroom;
import com.example.studentmanagementsystem.entity.Course;
import com.example.studentmanagementsystem.entity.Role;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.repository.ClassroomRepository;
import com.example.studentmanagementsystem.repository.CourseRepository;
import com.example.studentmanagementsystem.repository.UserRepository;
import com.example.studentmanagementsystem.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClassroomRepository classroomRepository;
    private final CourseRepository courseRepository;

    public AdminController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder,
            ClassroomRepository classroomRepository, CourseRepository courseRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.classroomRepository = classroomRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/users/create-account")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserRequestDTO request) {
        UserResponseDTO newUser = userService.createUser(request);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    // get list of all users
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/users/{userId}/reset-password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> resetToDefault(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not exist"));

        String defaultPassword = "123456";
        user.setPassword(passwordEncoder.encode(defaultPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);

        return ResponseEntity.ok("Password of [" + user.getUsername() + "] was reset to: " + defaultPassword);
    }

    // JS will call it to take num and fill
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> getDashboardStats() {

        // call repo to count user by role
        long totalStudents = userRepository.countByRole(Role.STUDENT);
        long totalTeachers = userRepository.countByRole(Role.TEACHER);

        // count
        long totalCourses = courseRepository.count();
        long totalClasses = classroomRepository.count();

        Map<String, Long> stats = new HashMap<>();
        stats.put("students", totalStudents);
        stats.put("teachers", totalTeachers);
        stats.put("courses", totalCourses);
        stats.put("classes", totalClasses);

        // return JSON: {"students": 50, "teachers": 10, ...}
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/chart-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getChartData() {
        Map<String, Object> response = new HashMap<>();

        // 1. Data for Pie Chart (User Roles)
        long totalStudents = userRepository.countByRole(Role.STUDENT);
        long totalTeachers = userRepository.countByRole(Role.TEACHER);
        long totalAdmins = userRepository.countByRole(Role.ADMIN);

        response.put("roles", List.of(totalStudents, totalTeachers, totalAdmins));

        // 2. Data for Bar Chart (Classes per Course)
        List<Object[]> courseStats = classroomRepository.countClassesByCourse();

        List<String> courseLabels = new ArrayList<>();
        List<Long> classCounts = new ArrayList<>();

        for (Object[] row : courseStats) {
            courseLabels.add((String) row[0]); // Course name
            classCounts.add((Long) row[1]); // Number of class
        }

        response.put("courseLabels", courseLabels);
        response.put("classCounts", classCounts);

        return ResponseEntity.ok(response);
    }

    // get 5 recent users
    @GetMapping("/recent-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getRecentUsers() {
        List<User> users = userRepository.findTop5ByOrderByIdDesc();

        // Convert Entity to DTO to hide password
        List<UserResponseDTO> dtos = users.stream().map(user -> new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.getIsActive())).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // get all teachers
    @GetMapping("/teachers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllTeachers() {
        List<User> teachers = userRepository.findByRole(Role.TEACHER);
        // Convert to DTO
        List<UserResponseDTO> dtos = teachers.stream()
                .map(t -> new UserResponseDTO(t.getId(), t.getFullName(), t.getUsername(), t.getEmail(),
                        t.getRole().name(), t.getIsActive()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}