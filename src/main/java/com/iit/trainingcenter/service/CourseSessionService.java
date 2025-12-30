package com.iit.trainingcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.iit.trainingcenter.entity.CourseSession;
import com.iit.trainingcenter.repository.CourseSessionRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseSessionService {

    private final CourseSessionRepository repository;

    public CourseSessionService(CourseSessionRepository repository) {
        this.repository = repository;
    }

    public void saveSession(CourseSession session) {
        // Vérifier que les objets requis existent
        if (session.getTrainer() == null) {
            throw new RuntimeException("Trainer is required");
        }

        if (session.getSpecialty() == null) {
            throw new RuntimeException("Specialty is required");
        }

        // Vérifier les conflits d'horaire
        List<CourseSession> conflicts =
                repository.findConflicts(
                        session.getDate(),
                        session.getStartTime(),
                        session.getEndTime(),
                        session.getTrainer().getId(),
                        session.getSpecialty().getId()
                );

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Schedule conflict detected");
        }

        repository.save(session);
    }

    public List<CourseSession> findBySpecialty(Long specialtyId) {
        if (specialtyId == null) {
            return new ArrayList<>();
        }

        try {
            List<CourseSession> sessions = repository.findBySpecialtyId(specialtyId);
            return sessions != null ? sessions : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error finding sessions by specialty: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}