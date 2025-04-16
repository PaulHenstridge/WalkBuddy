package com.ph.walkBuddy.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Default constructor
@AllArgsConstructor // Parameterized constructor
@Entity
public class Walk {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private LocalDateTime dateTime;

        @ManyToOne
        @JoinColumn(name = "location_id")
        private Location location;

        @ManyToMany
        @JoinTable(
                name = "walk_dogs",
                joinColumns = @JoinColumn(name = "walk_id"),
                inverseJoinColumns = @JoinColumn(name = "dog_id")
        )
        private List<Dog> dogs = new ArrayList<>();

        @OneToOne(mappedBy = "walk", cascade = CascadeType.ALL)
        @JoinColumn(name = "rating_id")
        private WalkRating rating;


        @OneToOne(mappedBy = "walk", cascade = CascadeType.ALL)
        @JoinColumn(name = "report_id")
        private DogReport report;
}