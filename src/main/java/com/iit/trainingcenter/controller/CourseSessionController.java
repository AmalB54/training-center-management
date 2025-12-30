package com.iit.trainingcenter.controller;

import com.iit.trainingcenter.entity.CourseSession;
import com.iit.trainingcenter.service.CourseSessionService;
import com.iit.trainingcenter.service.CourseService;
import com.iit.trainingcenter.service.TrainerService;
import com.iit.trainingcenter.service.SpecialtyService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/timetable")
public class CourseSessionController {

    private final CourseSessionService sessionService;
    private final CourseService courseService;
    private final TrainerService trainerService;
    private final SpecialtyService specialtyService;

    public CourseSessionController(
            CourseSessionService sessionService,
            CourseService courseService,
            TrainerService trainerService,
            SpecialtyService specialtyService
    ) {
        this.sessionService = sessionService;
        this.courseService = courseService;
        this.trainerService = trainerService;
        this.specialtyService = specialtyService;
    }

    @GetMapping
    public String viewTimetable(
            @RequestParam(required = false) Long specialtyId,
            Model model
    ) {
        try {
            // TOUJOURS charger les spécialités
            model.addAttribute("specialties", specialtyService.getAll());

            // TOUJOURS initialiser sessions (jamais null)
            if (specialtyId != null) {
                List<CourseSession> sessions = sessionService.findBySpecialty(specialtyId);
                model.addAttribute("sessions", sessions != null ? sessions : new ArrayList<>());
                model.addAttribute("selectedSpecialtyId", specialtyId);
            } else {
                model.addAttribute("sessions", new ArrayList<>());
                model.addAttribute("selectedSpecialtyId", null);
            }

            model.addAttribute("content", "admin/timetable");
            return "admin/layout";

        } catch (Exception e) {
            System.err.println("❌ ERROR in viewTimetable:");
            e.printStackTrace();

            // En cas d'erreur, TOUJOURS initialiser tous les attributs
            model.addAttribute("error", "Error loading timetable: " + e.getMessage());
            model.addAttribute("specialties", new ArrayList<>());
            model.addAttribute("sessions", new ArrayList<>());
            model.addAttribute("selectedSpecialtyId", null);
            model.addAttribute("content", "admin/timetable");
            return "admin/layout";
        }
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("session", new CourseSession());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("trainers", trainerService.getAllTrainers());
        model.addAttribute("specialties", specialtyService.getAll());
        model.addAttribute("content", "admin/session-form");
        return "admin/layout";
    }

    @PostMapping("/save")
    public String saveSession(@ModelAttribute CourseSession session,
                              @RequestParam(required = false) String courseCode,
                              @RequestParam(required = false) Long trainerId,
                              @RequestParam(required = false) Long specialtyId) {

        if (courseCode != null && !courseCode.isEmpty()) {
            session.setCourse(courseService.getCourseByCode(courseCode));
        }

        if (trainerId != null) {
            session.setTrainer(trainerService.getTrainerById(trainerId));
        }

        if (specialtyId != null) {
            session.setSpecialty(specialtyService.getById(specialtyId));
        }

        sessionService.saveSession(session);
        return "redirect:/admin/timetable?specialtyId=" + specialtyId;
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "CourseSessionController is working! " +
                "Courses: " + courseService.getAllCourses().size() + ", " +
                "Trainers: " + trainerService.getAllTrainers().size() + ", " +
                "Specialties: " + specialtyService.getAll().size();
    }
}