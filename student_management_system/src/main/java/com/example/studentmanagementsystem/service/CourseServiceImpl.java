package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.repository.CourseRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.studentmanagementsystem.entity.Course;;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course createCourse(Course course) {
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new RuntimeException("Course Code existed!");
        }
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
