package com.iit.trainingcenter.service;

import com.iit.trainingcenter.entity.Trainer;
import com.iit.trainingcenter.repository.TrainerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public Trainer saveTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public Trainer getTrainerById(Long id) {
        return trainerRepository.findById(id).orElse(null);
    }
    
    public void deleteTrainer(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
        
        // Vérifier les références
        boolean hasCourses = !trainerRepository.findCoursesByTrainerId(id).isEmpty();
        boolean hasSessions = !trainerRepository.findSessionsByTrainerId(id).isEmpty();
        
        if (hasCourses || hasSessions) {
            StringBuilder message = new StringBuilder("Cannot delete trainer '")
                    .append(trainer.getName())
                    .append("' because they are assigned to: ");
            
            if (hasCourses) {
                long count = trainerRepository.findCoursesByTrainerId(id).size();
                message.append(count).append(" course(s), ");
            }
            if (hasSessions) {
                long count = trainerRepository.findSessionsByTrainerId(id).size();
                message.append(count).append(" session(s), ");
            }
            
            message.setLength(message.length() - 2); // Enlever la dernière virgule
            throw new RuntimeException(message.toString());
        }
        
        trainerRepository.deleteById(id);
    }
}
