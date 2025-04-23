package com.ph.walkBuddy.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity

public class DogReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @ManyToOne
    @JoinColumn(name = "walk_id")
    private Walk walk;

    private String notes;

    private LocalDateTime createdAt;

    public DogReport(){};

    public DogReport(Dog dog, Walk walk, String notes) {
        this.dog = dog;
        this.walk = walk;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public Dog getDog() {
        return dog;
    }

    public Walk getWalk() {
        return walk;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public void setWalk(Walk walk) {
        this.walk = walk;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}


