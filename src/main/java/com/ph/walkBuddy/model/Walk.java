package com.ph.walkBuddy.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
@Entity
public class Walk {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private LocalDateTime dateTime;
        private Location location;
        @ManyToMany
        @JoinTable(
                name = "walk_dogs",
                joinColumns = @JoinColumn(name = "walk_id"),
                inverseJoinColumns = @JoinColumn(name = "dog_id")
        )
        private List<Dog> dogs = new ArrayList<>();

        private Rating rating;
        private Report report;
}
