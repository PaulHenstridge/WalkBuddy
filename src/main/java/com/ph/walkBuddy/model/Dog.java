package com.ph.walkBuddy.model;

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
    private Owner owner;

    @ManyToMany(mappedBy = "dogs")
    private List<Walk> walks = new ArrayList<>();

    private List<Rating> ratings = new ArrayList<>();
    private List<Report> reports = new ArrayList<>();

    private String notes;

}
