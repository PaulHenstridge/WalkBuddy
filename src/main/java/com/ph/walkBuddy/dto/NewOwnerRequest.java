// NewOwnerRequest.java
package com.ph.walkBuddy.dto;

import com.ph.walkBuddy.model.ContactDetails;

public class NewOwnerRequest {
    private String name;
    private ContactDetails contactDetails;
    private String notes;

    public String getName() {
        return name;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public String getNotes() {
        return notes;
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
