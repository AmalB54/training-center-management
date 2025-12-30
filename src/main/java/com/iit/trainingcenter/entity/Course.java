package com.iit.trainingcenter.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    private String code;

    private String title;
    private String description;
    
    @ManyToMany
    @JoinTable(
        name = "course_specialty",
        joinColumns = @JoinColumn(name = "course_code"),
        inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private List<Specialty> specialties;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    // Note: La relation Course-Student est gérée via l'entité Enrollment
    // qui permet de stocker des informations supplémentaires (enrollmentDate)

    public Course() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }
}

