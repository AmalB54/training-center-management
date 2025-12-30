package com.iit.trainingcenter.api;

import com.iit.trainingcenter.entity.Trainer;
import com.iit.trainingcenter.service.TrainerService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PreAuthorize("hasRole('ADMIN')")

@RestController
@RequestMapping("/api/trainers")
public class TrainerRestController {

    private final TrainerService trainerService;

    public TrainerRestController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    public List<Trainer> getAll() {
        return trainerService.getAllTrainers();
    }

    @GetMapping("/{id}")
    public Trainer getOne(@PathVariable Long id) {
        return trainerService.getTrainerById(id);
    }

    @PostMapping
    public Trainer save(@RequestBody Trainer trainer) {
        return trainerService.saveTrainer(trainer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
    }
}
