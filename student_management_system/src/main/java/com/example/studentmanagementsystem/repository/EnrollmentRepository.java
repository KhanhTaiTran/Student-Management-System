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

    List<Enrollment> findByClassRoomId(Long classRoomId);

    Optional<Enrollment> findByStudentIdAndClassRoomId(Long studentId, Long classRoomId);

    // delete enrollment by student id
    void deleteByStudentId(Long studentId);

    @Query("""
                SELECT COUNT(e)
                FROM Enrollment e
                WHERE e.classRoom.teacher.id = :teacherId
            """)
    long countTotalStudentsByTeacher(@Param("teacherId") Long teacherId);
}
