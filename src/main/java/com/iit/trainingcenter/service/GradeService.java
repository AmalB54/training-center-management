package com.iit.trainingcenter.service;

import com.iit.trainingcenter.entity.Grade;
import com.iit.trainingcenter.repository.GradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    // Pour STUDENT
    public List<Grade> getGradesByStudent(String matricule) {
        return gradeRepository.findByStudentMatricule(matricule);
    }

    // Pour TRAINER
    public Grade saveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    // Pour ADMIN
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }
}

