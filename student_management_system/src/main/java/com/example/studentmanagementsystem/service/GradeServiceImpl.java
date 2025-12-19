package com.example.studentmanagementsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.studentmanagementsystem.entity.Grade;
import com.example.studentmanagementsystem.repository.GradeRepository;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<Grade> getGradesByClass(Long classId) {
        return gradeRepository.findByClassroomId(classId);
    }

    @Override
    public Grade saveOrUpdate(Grade grade) {
        return gradeRepository.save(grade);
    }
}
