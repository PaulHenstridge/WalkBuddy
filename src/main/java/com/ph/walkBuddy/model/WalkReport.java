package com.ph.walkBuddy.model;

import jakarta.persistence.*;


import java.time.LocalDateTime;


@Entity

public class WalkReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "walk_id", unique = true)
    private Walk walk;

    private String notes;

    private LocalDateTime createdAt;

    public WalkReport() {};

    public WalkReport(Walk walk, String notes){
        this.walk = walk;
        this.notes = notes;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
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

    public void setWalk(Walk walk) {
        this.walk = walk;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
