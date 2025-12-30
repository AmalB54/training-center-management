package com.iit.trainingcenter.repository;

import com.iit.trainingcenter.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//string:type de la matricule
public interface StudentRepository extends JpaRepository<Student, String> {
    
    @Query("SELECT s FROM Student s WHERE s.specialty.id = :specialtyId")
    List<Student> findBySpecialtyId(@Param("specialtyId") Long specialtyId);
    
    boolean existsBySpecialtyId(Long specialtyId);
}
