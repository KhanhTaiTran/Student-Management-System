package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.repository.CourseRepository;

import java.util.List;
import java.util.Map;

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

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("The course with this id is not exists!");
        }

        courseRepository.deleteById(id);
    }

    @Override
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The course with this id is not exists!"));

        // update info of this course excep id
        course.setCourseName(courseDetails.getCourseName());
        // course code is unique
        course.setCourseCode(courseDetails.getCourseCode());
        course.setCredits(courseDetails.getCredits());
        course.setDescription(courseDetails.getDescription());

        return courseRepository.save(course);
    }

    @Override
    public Course patchCourse(Long id, Map<String, Object> updates) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "courseName":
                    course.setCourseName((String) value);
                    break;
                case "courseCode":
                    String newCode = (String) value;
                    // if change course code have to check unique or not
                    if (!newCode.equals(course.getCourseCode()) &&
                            courseRepository.existsByCourseCode(newCode)) {
                        throw new RuntimeException("Course Code already exists!");
                    }
                    course.setCourseCode(newCode);
                    break;
                case "credits":
                    course.setCredits((Integer) value);
                    break;
                case "description":
                    course.setDescription((String) value);
                    break;
            }
        });

        return courseRepository.save(course);
    }
}
