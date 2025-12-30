package com.iit.trainingcenter.service;

import com.iit.trainingcenter.entity.Specialty;
import com.iit.trainingcenter.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SpecialtyService {

    private final SpecialtyRepository repository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CourseSessionRepository courseSessionRepository;

    public SpecialtyService(SpecialtyRepository repository,
                           StudentRepository studentRepository,
                           CourseRepository courseRepository,
                           CourseSessionRepository courseSessionRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.courseSessionRepository = courseSessionRepository;
    }

    public List<Specialty> getAll() {
        return repository.findAll();
    }

    public Specialty save(Specialty specialty) {
        // Vérifier si on modifie une specialty existante
        if (specialty.getId() != null) {
            Specialty existing = repository.findById(specialty.getId()).orElse(null);
            if (existing != null && existing.getName().equals(specialty.getName())) {
                // Même nom, pas de problème
                return repository.save(specialty);
            }
        }
        // Nouvelle specialty ou changement de nom
        if (repository.existsByName(specialty.getName())) {
            throw new RuntimeException("Specialty already exists: " + specialty.getName());
        }
        return repository.save(specialty);
    }

    public void delete(Long id) {
        Specialty specialty = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found with id: " + id));
        
        // Vérifier les références
        boolean hasStudents = studentRepository.existsBySpecialtyId(id);
        boolean hasCourses = !courseRepository.findBySpecialtyId(id).isEmpty();
        boolean hasSessions = !courseSessionRepository.findBySpecialtyId(id).isEmpty();
        
        if (hasStudents || hasCourses || hasSessions) {
            StringBuilder message = new StringBuilder("Cannot delete specialty '")
                    .append(specialty.getName())
                    .append("' because it is referenced by: ");
            
            if (hasStudents) {
                long count = studentRepository.findBySpecialtyId(id).size();
                message.append(count).append(" student(s), ");
            }
            if (hasCourses) {
                message.append("course(s), ");
            }
            if (hasSessions) {
                message.append("session(s), ");
            }
            
            message.setLength(message.length() - 2); // Enlever la dernière virgule
            throw new RuntimeException(message.toString());
        }
        
        repository.deleteById(id);
    }
    
    public Specialty getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found with id: " + id));
    }

}
