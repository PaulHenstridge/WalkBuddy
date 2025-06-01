package com.ph.walkBuddy.dto;

import java.time.LocalDateTime;
import java.util.List;

public class NewWalkRequest {
    private Long locationId;
    private LocalDateTime dateTime;

    private List<Long> dogIds;



    public Long getLocationId() {
        return locationId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<Long> getDogIds() {
        return dogIds;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDogIds(List<Long> dogIds) {
        this.dogIds = dogIds;
    }
}
