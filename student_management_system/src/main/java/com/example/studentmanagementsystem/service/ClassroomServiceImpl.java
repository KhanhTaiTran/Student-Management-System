package com.example.studentmanagementsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.studentmanagementsystem.dto.request.CreateClassRequestDTO;
import com.example.studentmanagementsystem.repository.ClassroomRepository;
import com.example.studentmanagementsystem.repository.CourseRepository;
import com.example.studentmanagementsystem.repository.UserRepository;
import com.example.studentmanagementsystem.entity.Classroom;
import com.example.studentmanagementsystem.entity.Course;
import com.example.studentmanagementsystem.entity.User;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    private ClassroomRepository classroomRepository;

    private CourseRepository courseRepository;

    private UserRepository userRepository;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository, CourseRepository courseRepository,
            UserRepository userRepository) {
        this.classroomRepository = classroomRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Classroom createClassroom(CreateClassRequestDTO request) {
        // find course
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Can't find the course!"));
        // find lecturer
        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Can't find teacher!"));

        // check user is TEACHER or not
        if (!teacher.getRole().name().equals("TEACHER")) {
            throw new RuntimeException("This User ID is not a teacher!");
        }

        // create Class
        Classroom classroom = new Classroom();

        classroom.setClassName(request.getClassName());
        classroom.setCourse(course);
        classroom.setTeacher(teacher);
        classroom.setSemester(request.getSemester());
        classroom.setClassRoom(request.getClassRoom());

        return classroomRepository.save(classroom);
    }

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

}
