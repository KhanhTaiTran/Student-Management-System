package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.dto.request.CreateUserRequestDTO;
import com.example.studentmanagementsystem.dto.response.ScheduleResponseDTO;
import com.example.studentmanagementsystem.dto.response.StudentDashboardDTO;
import com.example.studentmanagementsystem.dto.response.UserResponseDTO;
import com.example.studentmanagementsystem.entity.Classroom;
import com.example.studentmanagementsystem.entity.Enrollment;
import com.example.studentmanagementsystem.entity.Notification;
import com.example.studentmanagementsystem.entity.Role;
import com.example.studentmanagementsystem.entity.Transaction;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.exception.ResourceNotFoundException;
import com.example.studentmanagementsystem.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradeRepository gradeRepository;
    private final TransactionRepository transactionRepository;
    private final ClassroomRepository classroomRepository;
    private final NotificationRepository notificationRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            RefreshTokenRepository refreshTokenRepository, EnrollmentRepository enrollmentRepository,
            GradeRepository gradeRepository, TransactionRepository transactionRepository,
            ClassroomRepository classroomRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.gradeRepository = gradeRepository;
        this.transactionRepository = transactionRepository;
        this.classroomRepository = classroomRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public UserResponseDTO createUser(CreateUserRequestDTO request) {
        // check username exist or not
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        // create user entity
        User user = new User();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // encode pass
        user.setIsActive(true);

        // Set Role
        Role role = Role.valueOf(request.getRole().toUpperCase());
        try {
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid Role! Allowed: ADMIN, TEACHER, STUDENT");
        }

        // check student ID
        if (role == Role.STUDENT) {
            if (request.getStudentId() == null || request.getStudentId().isEmpty()) {
                throw new RuntimeException("Student ID is required for Students!");
            }

            // check unique student id
            if (userRepository.existsByStudentId(request.getStudentId())) {
                throw new RuntimeException("Student ID already exist!");
            }
            user.setStudentId(request.getStudentId());
        } else {
            // teacher and admin don't need student ID
            user.setStudentId(null);
        }

        // save to db
        User savedUser = userRepository.save(user);

        // return to response
        return mapToResponse(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(user);
    }

    @Override
    @Transactional // use to ensure that erase all record relate to this id
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        refreshTokenRepository.deleteByUser(user);

        // if is student -> delete data register course
        if (user.getRole() == Role.STUDENT) {
            enrollmentRepository.deleteByStudentId(id);
        }

        // TEACHER -> delete classroom before delete user
        if (user.getRole() == Role.TEACHER) {
            classroomRepository.deleteByTeacherId(id);
        }

        // final -> delete users
        userRepository.delete(user);
    }

    @Override
    public StudentDashboardDTO getStudentDashboardInfo(Long studentId) {

        StudentDashboardDTO dto = new StudentDashboardDTO();

        // list of class that student enrolled
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        double totalScore = 0;
        int countSubjectWithGrade = 0;
        int totalCredits = 0;

        // loop for calculating GPA and total credits.
        for (Enrollment e : enrollments) {
            if (e.getClassRoom() != null && e.getClassRoom().getCourse() != null) {

                totalCredits += e.getClassRoom().getCourse().getCredits();
            }
            // have total grade or not?
            if (e.getTotalGrade() != null) {
                totalScore += e.getTotalGrade();
                countSubjectWithGrade++;
            }
        }

        // cal GPA
        double gpa = countSubjectWithGrade > 0 ? (totalScore / countSubjectWithGrade) : 0.0;

        dto.setGpa(Math.round(gpa * 100.0) / 100.0);
        dto.setTotalCredits(totalCredits);

        BigDecimal pricePerCredit = new BigDecimal("1526038");
        BigDecimal totalCost = pricePerCredit.multiply(new BigDecimal(totalCredits));

        List<Transaction> transactions = transactionRepository.findByStudentIdOrderByTransactionDateDesc(studentId);

        BigDecimal totalPaid = transactions.stream()
                .map(Transaction::getAmount)
                .filter(amount -> amount.compareTo(BigDecimal.ZERO) < 0)
                .map(BigDecimal::abs)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate the remaining debt
        BigDecimal remainingDebt = totalCost.subtract(totalPaid);

        if (remainingDebt.compareTo(BigDecimal.ZERO) < 0) {
            remainingDebt = BigDecimal.ZERO;
        }

        dto.setTuitionOwed(remainingDebt);

        // Schedule handle
        List<StudentDashboardDTO.ScheduleItem> scheduleItems = new ArrayList<>();

        // Java: Monday=1 ... Sunday=7
        // +1 => Monday=2 ... Sunday=8
        int todayInt = LocalDate.now().getDayOfWeek().getValue() + 1;

        for (Enrollment e : enrollments) {
            Classroom cls = e.getClassRoom();

            // Check if that class is having a session today.
            if (cls != null && cls.getDayOfWeek() != null && cls.getDayOfWeek() == todayInt) {
                StudentDashboardDTO.ScheduleItem item = new StudentDashboardDTO.ScheduleItem();

                // Null check for subject name
                String courseName = (cls.getCourse() != null) ? cls.getCourse().getCourseName() : "Unknown Course";

                item.setCourseName(courseName);
                item.setClassName(cls.getClassName());
                item.setRoom(cls.getClassRoom());
                item.setStartTime("Tiết " + cls.getStartPeriod());

                // Calculating the end lesson: Start + Number of lessons - 1
                int endPeriod = cls.getStartPeriod() + cls.getTotalPeriods() - 1;
                item.setEndTime("Tiết " + endPeriod);

                scheduleItems.add(item);
            }
        }
        dto.setTodaySchedule(scheduleItems);

        // notis
        List<StudentDashboardDTO.NotificationItem> notificationItems = new ArrayList<>();
        // 5 most recent notifications
        List<Notification> notifications = notificationRepository.findTop5ByUserIdOrderByCreatedAtDesc(studentId);

        for (Notification n : notifications) {
            StudentDashboardDTO.NotificationItem item = new StudentDashboardDTO.NotificationItem();
            item.setMessage(n.getMessage());
            // Convert date to string
            item.setCreatedAt(n.getCreatedAt().toLocalDate().toString());
            notificationItems.add(item);
        }
        dto.setNotifications(notificationItems);

        return dto;
    }

    @Override
    public User getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<ScheduleResponseDTO> getStudentSchedule(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        List<ScheduleResponseDTO> schedule = new ArrayList<>();

        for (Enrollment e : enrollments) {
            Classroom cls = e.getClassRoom();
            if (cls.getDayOfWeek() != null && cls.getStartPeriod() != null) {
                schedule.add(new ScheduleResponseDTO(
                        cls.getCourse().getCourseName(),
                        cls.getCourse().getCourseCode(),
                        cls.getClassName(),
                        cls.getClassRoom(),
                        cls.getTeacher().getFullName(),
                        cls.getDayOfWeek(),
                        cls.getStartPeriod(),
                        cls.getTotalPeriods()));
            }
        }
        return schedule;
    }

    // helper function
    private UserResponseDTO mapToResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.getIsActive());
    }
}
