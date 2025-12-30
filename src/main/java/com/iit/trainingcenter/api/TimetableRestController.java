package com.iit.trainingcenter.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iit.trainingcenter.entity.CourseSession;
import com.iit.trainingcenter.repository.CourseSessionRepository;

@RestController
@RequestMapping("/api/timetable")
public class TimetableRestController {

    private final CourseSessionRepository repository;

    public TimetableRestController(CourseSessionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/specialty/{id}")
    public List<CourseSession> getBySpecialty(@PathVariable Long id) {
        return repository.findBySpecialtyId(id);
    }
}
