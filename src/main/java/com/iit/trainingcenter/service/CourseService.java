package com.iit.trainingcenter.service;

import com.iit.trainingcenter.entity.Course;
import com.iit.trainingcenter.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradeRepository gradeRepository;
    private final CourseSessionRepository courseSessionRepository;

    public CourseService(CourseRepository courseRepository,
                        EnrollmentRepository enrollmentRepository,
                        GradeRepository gradeRepository,
                        CourseSessionRepository courseSessionRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.gradeRepository = gradeRepository;
        this.courseSessionRepository = courseSessionRepository;
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseByCode(String code) {
        return courseRepository.findById(code).orElse(null);
    }
    
    public void deleteCourse(String code) {
        Course course = courseRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Course not found with code: " + code));
        
        // Vérifier les références
        boolean hasEnrollments = enrollmentRepository.existsByCourse_Code(code);
        boolean hasGrades = gradeRepository.existsByCourseCode(code);
        boolean hasSessions = !courseSessionRepository.findByCourseCode(code).isEmpty();
        
        if (hasEnrollments || hasGrades || hasSessions) {
            StringBuilder message = new StringBuilder("Cannot delete course '")
                    .append(course.getCode())
                    .append(" - ")
                    .append(course.getTitle())
                    .append("' because it has: ");
            
            if (hasEnrollments) {
                long count = enrollmentRepository.findByCourseCode(code).size();
                message.append(count).append(" enrollment(s), ");
            }
            if (hasGrades) {
                long count = gradeRepository.findByCourseCode(code).size();
                message.append(count).append(" grade(s), ");
            }
            if (hasSessions) {
                long count = courseSessionRepository.findByCourseCode(code).size();
                message.append(count).append(" session(s), ");
            }
            
            message.setLength(message.length() - 2); // Enlever la dernière virgule
            throw new RuntimeException(message.toString());
        }
        
        courseRepository.deleteById(code);
    }
}
