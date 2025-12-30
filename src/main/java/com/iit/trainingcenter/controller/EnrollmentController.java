package com.iit.trainingcenter.controller;

import com.iit.trainingcenter.entity.Enrollment;
import com.iit.trainingcenter.entity.Student;
import com.iit.trainingcenter.entity.Course;
import com.iit.trainingcenter.service.EnrollmentService;
import com.iit.trainingcenter.service.StudentService;
import com.iit.trainingcenter.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentController(EnrollmentService enrollmentService,
                                StudentService studentService,
                                CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
        model.addAttribute("content", "admin/enrollments");
        return "admin/layout";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("content", "admin/enrollment-form");
        return "admin/layout";
    }

    @PostMapping("/save")
    public String save(@RequestParam String studentMatricule,
                       @RequestParam String courseCode) {

        Enrollment e = new Enrollment();
        e.setStudent(studentService.getStudentByMatricule(studentMatricule));
        e.setCourse(courseService.getCourseByCode(courseCode));
        e.setEnrollmentDate(LocalDate.now());

        enrollmentService.saveEnrollment(e);
        return "redirect:/admin/enrollments";
    }
}
