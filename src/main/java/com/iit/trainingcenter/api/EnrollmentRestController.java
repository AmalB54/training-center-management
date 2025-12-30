package com.iit.trainingcenter.api;

import com.iit.trainingcenter.entity.Enrollment;
import com.iit.trainingcenter.service.EnrollmentService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentRestController {

    private final EnrollmentService enrollmentService;

    public EnrollmentRestController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/student/{matricule}")
    @PreAuthorize("hasRole('ADMIN') or #matricule == authentication.name")

    public List<Enrollment> getByStudent(@PathVariable String matricule) {
        return enrollmentService.getByStudent(matricule);
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public Enrollment save(@RequestBody Enrollment enrollment) {
        return enrollmentService.saveEnrollment(enrollment);
    }
}

