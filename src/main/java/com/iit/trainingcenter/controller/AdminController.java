package com.iit.trainingcenter.controller;

import com.iit.trainingcenter.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StudentService studentService;
    private final TrainerService trainerService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final GradeService gradeService;
    private final SpecialtyService specialtyService;

    public AdminController(StudentService studentService,
                          TrainerService trainerService,
                          CourseService courseService,
                          EnrollmentService enrollmentService,
                          GradeService gradeService,
                          SpecialtyService specialtyService) {
        this.studentService = studentService;
        this.trainerService = trainerService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.gradeService = gradeService;
        this.specialtyService = specialtyService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("studentsCount", studentService.getAllStudents().size());
        model.addAttribute("trainersCount", trainerService.getAllTrainers().size());
        model.addAttribute("coursesCount", courseService.getAllCourses().size());
        model.addAttribute("enrollmentsCount", enrollmentService.getAllEnrollments().size());
        model.addAttribute("gradesCount", gradeService.getAllGrades().size());
        model.addAttribute("specialtiesCount", specialtyService.getAll().size());
        model.addAttribute("content", "admin/dashboard");
        return "admin/layout";
    }
}
