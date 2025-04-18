package com.ph.walkBuddy.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String w3wLocation;
    private String description;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    private String notes;


    public Location() {}

    public Location(String name, String w3wLocation, String description, String notes) {
        this.name = name;
        this.w3wLocation = w3wLocation;
        this.description = description;
        this.notes = notes;
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

    public String getW3wLocation() {
        return w3wLocation;
    }

    public void setW3wLocation(String w3wLocation) {
        this.w3wLocation = w3wLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", w3wLocation='" + w3wLocation + '\'' +
                ", description='" + description + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}


