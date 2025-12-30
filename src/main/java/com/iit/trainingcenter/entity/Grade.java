package com.iit.trainingcenter.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade_value")
    private Double value;

    @ManyToOne
    @JoinColumn(name = "student_matricule")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_code")
    private Course course;

    public Grade() {}

    public Long getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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
