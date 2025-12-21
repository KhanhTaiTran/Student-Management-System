package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // get all quiz of a classroom
    List<Quiz> findByClassroomId(Long classroomId);

    // Get all quizzes created by a teacher (based on classroom -> teacher)
    // This is used for the teacher's "My Quizzes" page
    @Query("SELECT q FROM Quiz q WHERE q.classroom.teacher.id = :teacherId")
    List<Quiz> findByClassroomTeacherId(Long teacherId);

    long countByClassroom_Teacher_Id(Long teacherId);

    @Query("SELECT q FROM Quiz q " +
            "JOIN q.classroom c " +
            "JOIN Enrollment e ON e.classRoom.id = c.id " +
            "WHERE e.student.id = :studentId")
    List<Quiz> findQuizzesByStudentId(@Param("studentId") Long studentId);
}