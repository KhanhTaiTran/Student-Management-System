package com.example.studentmanagementsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.studentmanagementsystem.dto.request.CreateClassRequestDTO;
import com.example.studentmanagementsystem.entity.Classroom;
import com.example.studentmanagementsystem.entity.Course;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.exception.ResourceNotFoundException;
import com.example.studentmanagementsystem.repository.ClassroomRepository;
import com.example.studentmanagementsystem.repository.CourseRepository;
import com.example.studentmanagementsystem.repository.UserRepository;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    private ClassroomRepository classroomRepository;
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    public ClassroomServiceImpl(
            ClassroomRepository classroomRepository,
            CourseRepository courseRepository,
            UserRepository userRepository) {
        this.classroomRepository = classroomRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    // ===================== CREATE =====================
    @Override
    public Classroom createClassroom(CreateClassRequestDTO request) {
        if (classroomRepository.existsByClassName(request.getClassName())) {
            throw new RuntimeException("Error: Class Name is unique!");
        }

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Can't find the course!"));

        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Can't find teacher!"));

        if (!teacher.getRole().name().equals("TEACHER")) {
            throw new RuntimeException("This User ID is not a teacher!");
        }

        Classroom classroom = new Classroom();
        classroom.setClassName(request.getClassName());
        classroom.setCourse(course);
        classroom.setTeacher(teacher);
        classroom.setSemester(request.getSemester());
        classroom.setClassRoom(request.getClassRoom());

        return classroomRepository.save(classroom);
    }

    // ===================== READ =====================
    @Override
    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    @Override
    public Classroom getClassroomById(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id: " + id));
    }

    // ✅ THÊM METHOD NÀY
    @Override
    public List<Classroom> getClassesByTeacher(Long teacherId) {
        return classroomRepository.findByTeacherId(teacherId);
    }

    // ===================== UPDATE =====================
    @Override
    public Classroom updateClassroom(Long id, CreateClassRequestDTO request) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id: " + id));

        if (!classroom.getClassName().equals(request.getClassName())
                && classroomRepository.existsByClassName(request.getClassName())) {
            throw new RuntimeException("Error: Class Name is unique!");
        }

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Can't find the course!"));

        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Can't find teacher!"));

        if (!teacher.getRole().name().equals("TEACHER")) {
            throw new RuntimeException("This User ID is not a teacher!");
        }

        classroom.setClassName(request.getClassName());
        classroom.setCourse(course);
        classroom.setTeacher(teacher);
        classroom.setSemester(request.getSemester());
        classroom.setClassRoom(request.getClassRoom());

        return classroomRepository.save(classroom);
    }

    // ===================== DELETE =====================
    @Override
    public void deleteClassroom(Long id) {
        if (!classroomRepository.existsById(id)) {
            throw new RuntimeException("The classroom with this id does not exist!");
        }
        classroomRepository.deleteById(id);
    }
}
