package com.example.studentmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.studentmanagementsystem.entity.Role;
import com.example.studentmanagementsystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByResetToken(String resetToken);

    Boolean existsByStudentId(String studentId);

    Optional<User> findByStudentId(String studentId);

    Optional<User> findByEmailIgnoreCase(String email);

    long countByRole(Role role);

    List<User> findTop5ByOrderByIdDesc();

    List<User> findByRole(Role role);

    // check overlap schedule for teacher
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
