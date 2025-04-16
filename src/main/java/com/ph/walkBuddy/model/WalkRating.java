package com.ph.walkBuddy.model;

import com.ph.walkBuddy.enums.RatingLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalkRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RatingLevel rating;


    private LocalDateTime ratedAt;


    @ManyToOne
    @JoinColumn(name = "walk_id", unique = true)
    private Walk walk;
}
