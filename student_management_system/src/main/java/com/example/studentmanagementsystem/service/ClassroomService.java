package com.example.studentmanagementsystem.service;

import java.util.List;

import com.example.studentmanagementsystem.dto.request.CreateClassRequestDTO;
import com.example.studentmanagementsystem.entity.Classroom;

public interface ClassroomService {
    Classroom createClassroom(CreateClassRequestDTO request);

    List<Classroom> getAllClassrooms();

    Classroom getClassroomById(Long id);

    Classroom updateClassroom(Long id, CreateClassRequestDTO request);

    void deleteClassroom(Long id);
}
