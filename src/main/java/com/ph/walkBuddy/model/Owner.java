package com.ph.walkBuddy.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Default constructor
@AllArgsConstructor // Parameterized constructor
@Entity
public class Owner {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        @Embedded
        private ContactDetails contactDetails;

        @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
        private List<Dog> dogs = new ArrayList<>();

        private String notes;

    }

