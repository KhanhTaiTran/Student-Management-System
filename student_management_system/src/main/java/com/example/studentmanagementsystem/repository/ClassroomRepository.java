package com.example.studentmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // check overlap schedule of teacher
    @Query("""
                SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END
                FROM Classroom c
                WHERE c.teacher.id = :teacherId
                  AND c.dayOfWeek = :dayOfWeek
                  AND (
                      (c.startPeriod < :endPeriod AND :startPeriod < (c.startPeriod + c.totalPeriods))
                  )
            """)
    boolean existsByTeacherAndSchedule(
            @Param("teacherId") Long teacherId,
            @Param("dayOfWeek") Integer dayOfWeek,
            @Param("startPeriod") Integer startPeriod,
            @Param("endPeriod") Integer endPeriod);
}
