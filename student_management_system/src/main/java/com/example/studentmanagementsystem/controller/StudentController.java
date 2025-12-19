package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.dto.response.StudentDashboardDTO;
import com.example.studentmanagementsystem.security.CustomUserDetails;
import com.example.studentmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public ResponseEntity<StudentDashboardDTO> getDashboard(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        StudentDashboardDTO dashboardInfo = userService.getStudentDashboardInfo(userDetails.getId());
        return ResponseEntity.ok(dashboardInfo);
    }
}
