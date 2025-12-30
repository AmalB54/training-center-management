package com.iit.trainingcenter.controller;

import com.iit.trainingcenter.entity.Specialty;
import com.iit.trainingcenter.entity.Student;
import com.iit.trainingcenter.service.SpecialtyService;
import com.iit.trainingcenter.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/students")
public class StudentController {
    private final SpecialtyService specialtyService;
    private final StudentService studentService;

    public StudentController(StudentService studentService, SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
        this.studentService = studentService;
    }

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("content", "admin/students");
        return "admin/layout";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("specialties", specialtyService.getAll());
        model.addAttribute("content", "admin/student-form");
        return "admin/layout";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student,
                              @RequestParam(required = false) Long specialtyId,
                              RedirectAttributes redirectAttributes) {
        try {
            // ✅ Fetch and set the specialty if provided
            if (specialtyId != null) {
                Specialty specialty = specialtyService.getById(specialtyId);
                student.setSpecialty(specialty);
            }

            // ✅ SAVE THE STUDENT
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("success", "Student saved successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/students";
    }

    @GetMapping("/delete/{matricule}")
    public String deleteStudent(@PathVariable String matricule, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(matricule);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/students";
    }
}