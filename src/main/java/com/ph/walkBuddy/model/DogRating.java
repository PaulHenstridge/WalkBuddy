package com.ph.walkBuddy.model;

import com.ph.walkBuddy.enums.RatingLevel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class DogRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @ManyToOne
    @JoinColumn(name = "walk_id")
    private Walk walk;

    @Enumerated(EnumType.STRING)
    private RatingLevel rating;

    private LocalDateTime ratedAt;

    public DogRating(){};

    public DogRating(Dog dog, Walk walk, RatingLevel rating) {
        this.dog = dog;
        this.walk = walk;
        this.rating = rating;
        this.ratedAt = LocalDateTime.now();
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

    public RatingLevel getRating() {
        return rating;
    }

    public LocalDateTime getRatedAt() {
        return ratedAt;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public void setWalk(Walk walk) {
        this.walk = walk;
    }

    public void setRating(RatingLevel rating) {
        this.rating = rating;
    }
}

