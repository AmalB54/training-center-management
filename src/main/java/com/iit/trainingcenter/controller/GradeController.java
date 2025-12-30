package com.iit.trainingcenter.controller;

import com.iit.trainingcenter.entity.Grade;
import com.iit.trainingcenter.entity.Student;
import com.iit.trainingcenter.entity.Course;
import com.iit.trainingcenter.service.GradeService;
import com.iit.trainingcenter.service.StudentService;
import com.iit.trainingcenter.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/grades")
public class GradeController {

    private final GradeService gradeService;
    private final StudentService studentService;
    private final CourseService courseService;

    public GradeController(GradeService gradeService,
                           StudentService studentService,
                           CourseService courseService) {
        this.gradeService = gradeService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("grades", gradeService.getAllGrades());
        model.addAttribute("content", "admin/grades");
        return "admin/layout";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("grade", new Grade());
        model.addAttribute("content", "admin/grade-form");
        return "admin/layout";
    }

    @PostMapping("/save")
    public String save(@RequestParam String studentMatricule,
                       @RequestParam String courseCode,
                       @RequestParam Double value) {

        Grade g = new Grade();
        g.setStudent(studentService.getStudentByMatricule(studentMatricule));
        g.setCourse(courseService.getCourseByCode(courseCode));
        g.setValue(value);

        gradeService.saveGrade(g);
        return "redirect:/admin/grades";
    }
}
