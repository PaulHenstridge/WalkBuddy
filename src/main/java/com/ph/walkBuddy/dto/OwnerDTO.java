package com.ph.walkBuddy.dto;

import com.ph.walkBuddy.model.ContactDetails;

import java.util.List;

public class OwnerDTO {
    private Long id;
    private String name;
    private ContactDetails contactDetails;
    private List<DogDTO> dogs;
    private String notes;

    public OwnerDTO() {}



    public List<DogDTO> getDogs() { return dogs; }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public String getNotes() {
        return notes;
    }

    public void setDogs(List<DogDTO> dogs) { this.dogs = dogs; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
