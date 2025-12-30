package com.iit.trainingcenter.service;

import com.iit.trainingcenter.entity.Student;
import com.iit.trainingcenter.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradeRepository gradeRepository;

    public StudentService(StudentRepository studentRepository,
                         EnrollmentRepository enrollmentRepository,
                         GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.gradeRepository = gradeRepository;
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByMatricule(String matricule) {
        return studentRepository.findById(matricule).orElse(null);
    }

    public void deleteStudent(String matricule) {
        Student student = studentRepository.findById(matricule)
                .orElseThrow(() -> new RuntimeException("Student not found with matricule: " + matricule));
        
        // Vérifier les références
        boolean hasEnrollments = enrollmentRepository.existsByStudent_Matricule(matricule);
        boolean hasGrades = gradeRepository.existsByStudentMatricule(matricule);
        
        if (hasEnrollments || hasGrades) {
            StringBuilder message = new StringBuilder("Cannot delete student '")
                    .append(student.getFirstName())
                    .append(" ")
                    .append(student.getLastName())
                    .append("' because they have: ");
            
            if (hasEnrollments) {
                long count = enrollmentRepository.findByStudent_Matricule(matricule).size();
                message.append(count).append(" enrollment(s), ");
            }
            if (hasGrades) {
                long count = gradeRepository.findByStudentMatricule(matricule).size();
                message.append(count).append(" grade(s), ");
            }
            
            message.setLength(message.length() - 2); // Enlever la dernière virgule
            throw new RuntimeException(message.toString());
        }
        
        studentRepository.deleteById(matricule);
    }
}
