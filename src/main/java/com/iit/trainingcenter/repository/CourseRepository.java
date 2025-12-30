package com.iit.trainingcenter.repository;

import com.iit.trainingcenter.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//string : clé primaire : code
public interface CourseRepository extends JpaRepository<Course, String> {
    
    @Query("SELECT DISTINCT c FROM Course c JOIN c.specialties s WHERE s.id = :specialtyId")
    List<Course> findBySpecialtyId(@Param("specialtyId") Long specialtyId);
}
