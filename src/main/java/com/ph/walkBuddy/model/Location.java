package com.ph.walkBuddy.model;

import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Location {
    private String name;
    private String location;

    private String description;

    private List<String> tags = new ArrayList<>();  // eg. Beach, Woodland, Park, Water, Open Spaces, Public Space

    private List<Rating> ratings = new ArrayList<>();
    private List<Report> reports = new ArrayList<>();

    private String notes;

}


