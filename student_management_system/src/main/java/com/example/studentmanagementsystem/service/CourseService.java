package com.example.studentmanagementsystem.service;

import java.util.List;
import java.util.Map;

import com.example.studentmanagementsystem.entity.Course;

public interface CourseService {
    // create course
    Course createCourse(Course course);

    // list the courses
    List<Course> getAllCourses();

    // delete course
    void deleteCourse(Long id);

    // edit course
    Course updateCourse(Long id, Course courseDetails);

    // edit using patch
    Course patchCourse(Long id, Map<String, Object> updates);
}
