package com.example.studentmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.studentmanagementsystem.entity.Classroom;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    // ================= BASIC =================

    // find class using classname
    Optional<Classroom> findByClassName(String className);

    Boolean existsByClassName(String className);

    // ================= COURSE =================

    // find all classrooms of a specific course
    List<Classroom> findByCourseId(Long courseId);

    // ================= TEACHER =================

    // find all classrooms a teacher is teaching
    List<Classroom> findByTeacherId(Long teacherId);

    // ✅ COUNT total teaching classes (DÙNG CHO DASHBOARD)
    long countByTeacherId(Long teacherId);

    Boolean existsByTeacherId(Long teacherId);

    void deleteByTeacherId(Long teacherId);

    // ================= STATISTIC =================

    // return list object: [Course name, Number of class]
    @Query("""
                SELECT c.courseName, COUNT(cl)
                FROM Classroom cl
                JOIN cl.course c
                GROUP BY c.courseName
            """)
    List<Object[]> countClassesByCourse();
}
