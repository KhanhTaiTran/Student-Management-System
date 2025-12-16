package com.example.studentmanagementsystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagementsystem.dto.request.CreateClassRequestDTO;
import com.example.studentmanagementsystem.entity.Classroom;
import com.example.studentmanagementsystem.service.ClassroomService;

@RestController
@RequestMapping("/api/admin/classrooms")
public class ClassroomController {

    private ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    // POST: http://localhost:8080/api/admin/classrooms
    @PostMapping
    public ResponseEntity<Classroom> createClassroom(@RequestBody CreateClassRequestDTO request) {
        return ResponseEntity.ok(classroomService.createClassroom(request));
    }

    // GET: http://localhost:8080/api/admin/classrooms
    @GetMapping
    public ResponseEntity<List<Classroom>> getAllClassrooms() {
        return ResponseEntity.ok(classroomService.getAllClassrooms());
    }
}