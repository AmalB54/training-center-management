package com.iit.trainingcenter.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollments",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"student_matricule", "course_code"})
       })
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate enrollmentDate;

    @ManyToOne
    @JoinColumn(name = "student_matricule")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_code")
    private Course course;

    public Enrollment() {}

    public Long getId() {
        return id;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

