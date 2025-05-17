package com.ph.walkBuddy.model;

import jakarta.persistence.Embeddable;


    @Embeddable

    public class ContactDetails {
        private String address;
        private String phoneNumber;
        private String email;


        public ContactDetails() {
        }

        public String getAddress() {
            return address;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


