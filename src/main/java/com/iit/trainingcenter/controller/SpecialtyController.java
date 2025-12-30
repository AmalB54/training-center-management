package com.iit.trainingcenter.controller;

import com.iit.trainingcenter.entity.Specialty;
import com.iit.trainingcenter.service.SpecialtyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/specialties")
public class SpecialtyController {

    private final SpecialtyService service;

    public SpecialtyController(SpecialtyService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("specialties", service.getAll());
        model.addAttribute("specialty", new Specialty());
        model.addAttribute("content", "admin/specialties");
        return "admin/layout";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Specialty specialty, RedirectAttributes redirectAttributes) {
        try {
            service.save(specialty);
            redirectAttributes.addFlashAttribute("success", "Specialty saved successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/specialties";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("success", "Specialty deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/specialties";
    }
}
