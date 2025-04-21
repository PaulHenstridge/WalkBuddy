package com.ph.walkBuddy;

import com.ph.walkBuddy.model.Location;
import com.ph.walkBuddy.repository.LocationRepository;
import com.ph.walkBuddy.service.LocationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
public class LocationServiceTest {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationService locationService;

    @Test
    void testCreateLocation() {
        Location location = new Location("Park", "green.grass.sun", "A big park", "Watch out for geese");

        Location saved = locationService.createLocation(location);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Park");
    }

    @Test
    void testGetAllLocations() {
        Location loc1 = new Location("Forest", "trees.shade.trail", "Shady trail", "Ticks in summer");
        Location loc2 = new Location("Beach", "sand.sun.sea", "Sunny beach", "Off-lead allowed");

        locationRepository.save(loc1);
        locationRepository.save(loc2);

        List<Location> locations = locationService.getAllLocations();

        assertThat(locations).hasSize(2);
    }
    @Test
    void testGetLocationById() {
        // Arrange
        Location loc = new Location("Field", "green.field.trees", "Grassy area", "Can get muddy");
        Location savedLoc = locationRepository.save(loc);  // DB will assign ID
        Long savedId = savedLoc.getId();  // use ID in assertions

        // Act
        Location found = locationService.getLocationById(savedId);

        //Assert
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Field");
        assertThat(found.getW3wLocation()).isEqualTo("green.field.trees");
    }

    @Test
    void testUpdateLocation() {
        Location original = new Location("Field", "flat.grass.area", "Open field", "Muddy when wet");
        Location saved = locationRepository.save(original);

        Location updates = new Location("Field Updated", "new.code.here", "Updated desc", "Dry in spring");

        Location updated = locationService.updateLocation(saved.getId(), updates);

        assertThat(updated.getName()).isEqualTo("Field Updated");
        assertThat(updated.getDescription()).isEqualTo("Updated desc");
    }

    @Test
    void testDeleteLocation() {
        //Arrange
        Location location = new Location("Park", "calm.park.trees", "Nice park", "Avoid weekends");
        Location saved = locationRepository.save(location);
        // Act
        locationService.deleteLocation(saved.getId());
        // Assert
        assertThat(locationRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void testGetLocationByIdThrowsIfNotFound() {
        Long invalidId = 999L;

        assertThatThrownBy(() -> locationService.getLocationById(invalidId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Location not found");
    }

    @Test
    void testDeleteLocationThrowsIfNotFound() {
        Long invalidId = 888L;

        assertThatThrownBy(() -> locationService.deleteLocation(invalidId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Location not found");
    }
}
