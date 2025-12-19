package com.example.studentmanagementsystem.service;

import java.util.List;

import com.example.studentmanagementsystem.entity.Course;

public interface CourseService {
    // create course
    Course createCourse(Course course);

    // list the courses
    List<Course> getAllCourses();
}
