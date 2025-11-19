package com.ph.walkBuddy.model;

import com.ph.walkBuddy.enums.RatingLevel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity

public class WalkRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "walk_id", nullable = false, unique = true)
    private Walk walk;
    @Enumerated(EnumType.STRING)
    private RatingLevel rating;

    private LocalDateTime ratedAt;

    public WalkRating() {}

    public WalkRating(Walk walk, RatingLevel rating ){
        this.walk = walk;
        this.rating = rating;
        this.ratedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Walk getWalk() {
        return walk;
    }

    public RatingLevel getRating() {
        return rating;
    }

    public LocalDateTime getRatedAt() {
        return ratedAt;
    }

    public void setWalk(Walk walk) {
        this.walk = walk;
    }

    public void setRating(RatingLevel rating) {
        this.rating = rating;
    }
}
