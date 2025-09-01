package com.ph.walkBuddy.dto;

import com.ph.walkBuddy.model.Location;
import com.ph.walkBuddy.model.WalkReport;

import java.time.LocalDateTime;
import java.util.List;

public class WalkDTO {
    private Long id;
    private Long locationId;
    private String locationName;
    private LocalDateTime dateTime;

    private List<DogDTO> dogs;
    private boolean complete;
    private String report;

    public WalkDTO() {}

    public WalkDTO(Long id, Long locationId, String locationName,LocalDateTime dateTime,
                   List<DogDTO> dogs, boolean complete, String report) {
        this.id = id;

        this.locationId = locationId;
        this.locationName = locationName;
        this.dateTime = dateTime;
        this.dogs = dogs;
        this.complete = complete;
        this.report = report;
    }

    public Long getId() {
        return id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<DogDTO> getDogs() {
        return dogs;
    }

    public boolean isComplete() {
        return complete;
    }

    public String getReport() {
        return report;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLocation(Long locationId) {
        this.locationId = locationId;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDogs(List<DogDTO> dogs) {
        this.dogs = dogs;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
