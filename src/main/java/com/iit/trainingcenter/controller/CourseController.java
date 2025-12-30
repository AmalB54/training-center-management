package com.iit.trainingcenter.controller;

import com.iit.trainingcenter.entity.Course;
import com.iit.trainingcenter.service.CourseService;
import com.iit.trainingcenter.service.TrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/courses")
public class CourseController {

    private final CourseService courseService;
    private final TrainerService trainerService;

    public CourseController(CourseService courseService, TrainerService trainerService) {
        this.courseService = courseService;
        this.trainerService = trainerService;
    }

    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("content", "admin/courses");
        return "admin/layout";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("trainers", trainerService.getAllTrainers());
        model.addAttribute("content", "admin/course-form");
        return "admin/layout";
    }

    @PostMapping("/save")
    public String saveCourse(@ModelAttribute Course course,
                             @RequestParam(required = false) Long trainerId,
                             RedirectAttributes redirectAttributes) {
        try {
            if (trainerId != null) {
                course.setTrainer(trainerService.getTrainerById(trainerId));
            }
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("success", "Course saved successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/courses";
    }
    
    @GetMapping("/edit/{code}")
    public String editForm(@PathVariable String code, Model model) {
        model.addAttribute("course", courseService.getCourseByCode(code));
        model.addAttribute("trainers", trainerService.getAllTrainers());
        model.addAttribute("content", "admin/course-form");
        return "admin/layout";
    }

    @GetMapping("/delete/{code}")
    public String delete(@PathVariable String code, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(code);
            redirectAttributes.addFlashAttribute("success", "Course deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/courses";
    }
}
