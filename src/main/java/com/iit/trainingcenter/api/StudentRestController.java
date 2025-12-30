package com.iit.trainingcenter.api;

import com.iit.trainingcenter.entity.Student;
import com.iit.trainingcenter.service.StudentService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAll() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{matricule}")
    public Student getOne(@PathVariable String matricule) {
        return studentService.getStudentByMatricule(matricule);
    }

    @PostMapping
    public Student save(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @DeleteMapping("/{matricule}")
    public void delete(@PathVariable String matricule) {
        studentService.deleteStudent(matricule);
    }
}
