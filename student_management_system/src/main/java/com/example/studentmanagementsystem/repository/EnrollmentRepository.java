package com.example.studentmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.studentmanagementsystem.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // check is that sutudent register this class or not
    Boolean existsByStudentIdAndClassRoomId(Long studentId, Long classRoomId);

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByClassRoomId(Long classId);

    boolean existsByStudentIdAndClassRoomCourseId(Long studentId, Long courseId);

    Optional<Enrollment> findByStudentIdAndClassRoomId(Long studentId, Long classRoomId);

    long countByClassRoom_Teacher_Id(Long teacherId);

    // delete enrollment by student id
    void deleteByStudentId(Long studentId);

    @Query("""
                SELECT COUNT(e)
                FROM Enrollment e
                WHERE e.classRoom.teacher.id = :teacherId
            """)
    long countTotalStudentsByTeacher(@Param("teacherId") Long teacherId);

    long countByClassRoomId(Long classRoomId);

    // is student has been enrolled in this course?
    @Query("""
                SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END
                FROM Enrollment e
                WHERE e.student.id = :studentId
                  AND e.classRoom.course.id = :courseId
            """)
    boolean hasStudentEnrolledCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    // is student schedule conflicted?
    @Query("""
                SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END
                FROM Enrollment e
                WHERE e.student.id = :studentId
                  AND e.classRoom.dayOfWeek = :dayOfWeek
                  AND (
                      (e.classRoom.startPeriod < :endPeriod AND :startPeriod < (e.classRoom.startPeriod + e.classRoom.totalPeriods))
                  )
            """)
    boolean isStudentScheduleConflicted(
            @Param("studentId") Long studentId,
            @Param("dayOfWeek") Integer dayOfWeek,
            @Param("startPeriod") Integer startPeriod,
            @Param("endPeriod") Integer endPeriod);
}
