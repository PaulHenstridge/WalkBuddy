package com.ph.walkBuddy;

import com.ph.walkBuddy.model.Location;
import com.ph.walkBuddy.repository.LocationRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void testCreateLocationWithTags() {
        Location location = new Location();
        location.setName("Riverside Park");
        location.setW3wLocation("river.walking.path");
        location.setDescription("Scenic trail near the river");
        location.setTags(List.of("Water", "Woodland"));

        Location saved = locationRepository.save(location);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTags()).contains("Water", "Woodland");
    }

    @Test
    @DisplayName("Can save and retrieve a Location")
    void testSaveAndFind() {
        Location loc = new Location("Beach Park", "beach.park.area", "Great walk", "Watch for tides");
        locationRepository.save(loc);

        List<Location> all = locationRepository.findAll();

        assertThat(all).isNotEmpty();
        assertThat(all.get(0).getName()).isEqualTo("Beach Park");
    }
}
