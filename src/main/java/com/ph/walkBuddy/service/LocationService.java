package com.ph.walkBuddy.service;

import com.ph.walkBuddy.model.Dog;
import com.ph.walkBuddy.model.Location;
import com.ph.walkBuddy.model.Owner;
import com.ph.walkBuddy.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;


    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    };
    // get all locations
    public List<Location> getAllLocations() {return locationRepository.findAll();}

    // Get single location
    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id: " + id));
    }

    // Create new location
    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    // Update existing location
    public Location updateLocation(Long id, Location updatedLocation) {
        Location existingLocation = getLocationById(id);
        existingLocation.setName(updatedLocation.getName());
        existingLocation.setW3wLocation(updatedLocation.getW3wLocation());
        existingLocation.setDescription(updatedLocation.getDescription());
        existingLocation.setNotes(updatedLocation.getNotes());
        return locationRepository.save(existingLocation);
    }


    // Delete location
    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new EntityNotFoundException("Location not found with id: " + id);
        }
        locationRepository.deleteById(id);
    }
}
