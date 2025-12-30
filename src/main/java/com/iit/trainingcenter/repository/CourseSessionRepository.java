package com.iit.trainingcenter.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iit.trainingcenter.entity.CourseSession;

public interface CourseSessionRepository
        extends JpaRepository<CourseSession, Long> {

    List<CourseSession> findBySpecialtyId(Long specialtyId);
    
    List<CourseSession> findByCourseCode(String courseCode);

    @Query("""
        SELECT cs FROM CourseSession cs
        WHERE cs.date = :date
        AND (:start < cs.endTime AND :end > cs.startTime)
        AND (cs.trainer.id = :trainerId
             OR cs.specialty.id = :specialtyId)
    """)
    List<CourseSession> findConflicts(
            LocalDate date,
            LocalTime start,
            LocalTime end,
            Long trainerId,
            Long specialtyId
    );
}
