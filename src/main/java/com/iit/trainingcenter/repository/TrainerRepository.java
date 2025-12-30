package com.iit.trainingcenter.repository;

import com.iit.trainingcenter.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    
    @Query("SELECT c FROM Course c WHERE c.trainer.id = :trainerId")
    List<com.iit.trainingcenter.entity.Course> findCoursesByTrainerId(@Param("trainerId") Long trainerId);
    
    @Query("SELECT cs FROM CourseSession cs WHERE cs.trainer.id = :trainerId")
    List<com.iit.trainingcenter.entity.CourseSession> findSessionsByTrainerId(@Param("trainerId") Long trainerId);
}
