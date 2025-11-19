package com.ph.walkBuddy.model;

import com.ph.walkBuddy.enums.RatingLevel;
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

        @OneToOne(mappedBy = "walk", cascade = CascadeType.ALL, orphanRemoval = true)
        private WalkRating rating;


        @OneToOne(mappedBy = "walk", cascade = CascadeType.ALL)
        private WalkReport report;

        private boolean complete = false;



        public Walk() {}

        public Walk(LocalDateTime dateTime, Location location) {
                this.dateTime = dateTime;
                this.location = location;
        }

        // --- Getters and Setters ---

        public Long getId() {
                return id;
        }

        public LocalDateTime getDateTime() {
                return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
                this.dateTime = dateTime;
        }

        public Location getLocation() {
                return location;
        }

        public void setLocation(Location location) {
                this.location = location;
        }

        public List<Dog> getDogs() {
                return dogs;
        }

        public void setDogs(List<Dog> dogs) {
                this.dogs = dogs;
        }

        public WalkRating getRating() {
                return rating;
        }

        public void setRating(WalkRating rating) {
                this.rating = rating;
        }

        public WalkReport getReport() {
                return report;
        }

        public void setReport(WalkReport report) {
                this.report = report;
        }

        public boolean isComplete() {
                return complete;
        }

        public void setComplete(boolean complete) {
                this.complete = complete;
        }

        public void rate(RatingLevel level) {
                if (this.rating == null) {
                        this.rating = new WalkRating(this, level);
                } else {
                        this.rating.setRating(level);
                }
        }

        @Override
        public String toString() {
                return "Walk{" +
                        "id=" + id +
                        ", dateTime=" + dateTime +
                        ", location=" + (location != null ? location.getId() : "null") +
                        '}';
        }


}