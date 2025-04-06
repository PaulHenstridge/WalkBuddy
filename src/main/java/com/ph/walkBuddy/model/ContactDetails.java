package com.ph.walkBuddy.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ContactDetails {
        private String Address;
        private String phoneNumber;
        private String email;
    }

