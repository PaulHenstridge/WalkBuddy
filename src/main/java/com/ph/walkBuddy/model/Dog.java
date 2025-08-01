package com.ph.walkBuddy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String breed;
    private String description;
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)

    private Owner owner;

    @ManyToMany(mappedBy = "dogs")
    private List<Walk> walks = new ArrayList<>();

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DogRating> dogRatings = new ArrayList<>();

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DogReport> dogReports = new ArrayList<>();

    private String notes;

    public Dog() {}

    public Dog(String name, String breed, String description, LocalDate dateOfBirth, String notes, Owner owner) {
        this.name = name;
        this.breed = breed;
        this.description = description;
        this.dateOfBirth = dateOfBirth;
        this.notes = notes;
        this.owner = owner;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Walk> getWalks() {
        return walks;
    }

    public void setWalks(List<Walk> walks) {
        this.walks = walks;
    }

    public List<DogRating> getDogRatings() {
        return dogRatings;
    }

    public void setDogRatings(List<DogRating> dogRatings) {
        this.dogRatings = dogRatings;
    }

    public List<DogReport> getDogReports() {
        return dogReports;
    }

    public void setDogReports(List<DogReport> dogReports) {
        this.dogReports = dogReports;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + (owner != null ? owner.getId() : "null") +
                ", notes='" + notes + '\'' +
                '}';
    }
}


