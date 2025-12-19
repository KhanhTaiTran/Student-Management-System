package com.example.studentmanagementsystem.service;

import java.util.List;
import com.example.studentmanagementsystem.entity.Grade;

public interface GradeService {

    List<Grade> getGradesByClass(Long classId);

    Grade saveOrUpdate(Grade grade);
}
