package com.ph.walkBuddy.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Owner {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        @Embedded
        private ContactDetails contactDetails;

        @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Dog> dogs = new ArrayList<>();

        private String notes;

        public Owner() {}

        public Owner(String name, ContactDetails contactDetails, String notes) {
                this.name = name;
                this.contactDetails = contactDetails;
                this.notes = notes;
        }

        // --- Getters and Setters ---

        public Long getId() {
                return id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public ContactDetails getContactDetails() {
                return contactDetails;
        }

        public void setContactDetails(ContactDetails contactDetails) {
                this.contactDetails = contactDetails;
        }

        public List<Dog> getDogs() {
                return dogs;
        }

        public void setDogs(List<Dog> dogs) {
                this.dogs = dogs;
        }

        public String getNotes() {
                return notes;
        }

        public void setNotes(String notes) {
                this.notes = notes;
        }

        @Override
        public String toString() {
                return "Owner{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", contactDetails=" + contactDetails +
                        ", notes='" + notes + '\'' +
                        '}';
        }
}



