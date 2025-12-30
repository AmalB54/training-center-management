package com.iit.trainingcenter.repository;

import com.iit.trainingcenter.entity.Enrollment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent_Matricule(String matricule);
    
    @Query("SELECT e FROM Enrollment e WHERE e.course.code = :courseCode")
    List<Enrollment> findByCourseCode(@Param("courseCode") String courseCode);
    
    boolean existsByStudent_Matricule(String matricule);
    boolean existsByCourse_Code(String courseCode);
}
