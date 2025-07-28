package com.ph.walkBuddy.dto;

import com.ph.walkBuddy.model.Location;
import com.ph.walkBuddy.model.WalkReport;

import java.time.LocalDateTime;
import java.util.List;

public class WalkDTO {
    private Long id;
    private Location location;
    private LocalDateTime dateTime;

    private List<DogDTO> dogs;
    private boolean complete;
    private WalkReport report;

    public Long getId() {
        return id;
    }

    public Location getLocation() {
        return location;
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

    public WalkReport getReport() {
        return report;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public void setReport(WalkReport report) {
        this.report = report;
    }
}
