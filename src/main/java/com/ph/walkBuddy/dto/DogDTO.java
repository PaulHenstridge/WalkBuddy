package com.ph.walkBuddy.dto;


import java.time.LocalDate;

public class DogDTO {
    private Long id;
    private String name;
    private String breed;
    private LocalDate dateOfBirth;
    private String description;
    private String notes;
    private OwnerSummary owner;


    public DogDTO() {}




    public Long getId() { return id; }

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

    public OwnerSummary getOwner() { return owner; }

    public void setId(Long id) { this.id = id; }

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

    public void setOwner(OwnerSummary owner) { this.owner = owner; }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
