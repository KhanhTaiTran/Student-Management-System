package com.example.studentmanagementsystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagementsystem.dto.request.CreateClassRequestDTO;
import com.example.studentmanagementsystem.entity.Classroom;
import com.example.studentmanagementsystem.service.ClassroomService;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {

    private ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    // POST: http://localhost:8080/api/classrooms
    @PostMapping
    public ResponseEntity<Classroom> createClassroom(@RequestBody CreateClassRequestDTO request) {
        return ResponseEntity.ok(classroomService.createClassroom(request));
    }

    // GET: http://localhost:8080/api/classrooms
    @GetMapping
    public ResponseEntity<List<Classroom>> getAllClassrooms() {
        return ResponseEntity.ok(classroomService.getAllClassrooms());
    }

    // GET: http://localhost:8080/api/classrooms/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getClassroomById(@PathVariable Long id) {
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }

    // PUT: http://localhost:8080/api/classrooms/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Classroom> updateClassroom(@PathVariable Long id,
            @RequestBody CreateClassRequestDTO request) {
        return ResponseEntity.ok(classroomService.updateClassroom(id, request));
    }

    // DELETE: http://localhost:8080/api/classrooms/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.ok("Classroom deleted successful!");
    }
}