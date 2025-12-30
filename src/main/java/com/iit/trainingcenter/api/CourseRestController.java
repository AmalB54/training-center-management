package com.iit.trainingcenter.api;

import com.iit.trainingcenter.entity.Course;
import com.iit.trainingcenter.entity.Trainer;
import com.iit.trainingcenter.service.CourseService;
import com.iit.trainingcenter.service.TrainerService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {

    private final CourseService courseService;
    private final TrainerService trainerService;

    public CourseRestController(CourseService courseService,
                                TrainerService trainerService) {
        this.courseService = courseService;
        this.trainerService = trainerService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT','TRAINER')")

    public List<Course> getAll() {
        return courseService.getAllCourses();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")

    public Course save(@RequestBody Course course,
                       @RequestParam(required = false) Long trainerId) {

        if (trainerId != null) {
            Trainer t = trainerService.getTrainerById(trainerId);
            course.setTrainer(t);
        }
        return courseService.saveCourse(course);
    }
}
