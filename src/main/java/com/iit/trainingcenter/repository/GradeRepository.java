package com.iit.trainingcenter.repository;

import com.iit.trainingcenter.entity.Grade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentMatricule(String matricule);
    
    @Query("SELECT g FROM Grade g WHERE g.course.code = :courseCode")
    List<Grade> findByCourseCode(@Param("courseCode") String courseCode);
    
    boolean existsByStudentMatricule(String matricule);
    boolean existsByCourseCode(String courseCode);
}
