package com.ph.walkBuddy.controller;



import com.ph.walkBuddy.model.Dog;
import com.ph.walkBuddy.model.Location;
import com.ph.walkBuddy.model.Owner;
import com.ph.walkBuddy.service.LocationService;
import com.ph.walkBuddy.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@CrossOrigin(origins = "http://localhost:3000")

public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // GET /locations - retrieve all
    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    // GET /locations/{id} - retrieve one
    @GetMapping("/{id}")
    public Location getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    // POST /locations - create new
    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationService.createLocation(location);
    }

    // PUT /locations/{id} - update
    @PutMapping("/{id}")
    public Location updateLocation(@PathVariable Long id, @RequestBody Location updatedLocation) {
        return locationService.updateLocation(id, updatedLocation);
    }

    // DELETE /locations/{id} - delete
    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }

}
