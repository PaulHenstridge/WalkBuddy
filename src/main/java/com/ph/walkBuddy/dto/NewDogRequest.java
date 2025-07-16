package com.ph.walkBuddy.dto;

import java.time.LocalDate;

public class NewDogRequest {
    private String name;
    private String breed;
    private String description;
    private LocalDate dateOfBirth;
    private String notes;
    private Long ownerId;

    public NewDogRequest(String name, String breed, String description, LocalDate dateOfBirth, String notes, Long ownerId) {
        this.name = name;
        this.breed = breed;
        this.description = description;
        this.dateOfBirth = dateOfBirth;
        this.notes = notes;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public String getDescription() {
        return description;
    }

    public String getNotes() {
        return notes;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}