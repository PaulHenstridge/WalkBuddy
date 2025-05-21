package com.ph.walkBuddy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String breed;
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonBackReference
    private Owner owner;

    @ManyToMany(mappedBy = "dogs")
    private List<Walk> walks = new ArrayList<>();

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DogRating> dogRatings = new ArrayList<>();

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DogReport> dogReports = new ArrayList<>();

    private String notes;

    public Dog() {}

    public Dog(String name, String breed, String description, String notes, Owner owner) {
        this.name = name;
        this.breed = breed;
        this.description = description;
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


