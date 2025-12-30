package com.iit.trainingcenter.controller;

import com.iit.trainingcenter.entity.Trainer;
import com.iit.trainingcenter.service.TrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    public String listTrainers(Model model) {
        model.addAttribute("trainers", trainerService.getAllTrainers());
        model.addAttribute("content", "admin/trainers");
        return "admin/layout";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("trainer", new Trainer());
        model.addAttribute("content", "admin/trainer-form");
        return "admin/layout";
    }

    @PostMapping("/save")
    public String saveTrainer(@ModelAttribute Trainer trainer, RedirectAttributes redirectAttributes) {
        try {
            trainerService.saveTrainer(trainer);
            redirectAttributes.addFlashAttribute("success", "Trainer saved successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/trainers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            trainerService.deleteTrainer(id);
            redirectAttributes.addFlashAttribute("success", "Trainer deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/trainers";
    }
}
