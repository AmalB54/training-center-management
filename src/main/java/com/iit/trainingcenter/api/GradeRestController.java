package com.iit.trainingcenter.api;

import com.iit.trainingcenter.entity.Grade;
import com.iit.trainingcenter.service.GradeService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeRestController {

    private final GradeService gradeService;

    public GradeRestController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    // ADMIN
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")

    public List<Grade> getAll() {
        return gradeService.getAllGrades();
    }

    // STUDENT
    @PreAuthorize("hasRole('ADMIN') or #matricule == authentication.name")

    @GetMapping("/student/{matricule}")
    public List<Grade> getByStudent(@PathVariable String matricule) {
        return gradeService.getGradesByStudent(matricule);
    }

    // TRAINER
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")

    public Grade save(@RequestBody Grade grade) {
        return gradeService.saveGrade(grade);
    }
}
