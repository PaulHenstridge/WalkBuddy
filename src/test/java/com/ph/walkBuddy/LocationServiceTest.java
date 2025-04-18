package com.ph.walkBuddy;

import com.ph.walkBuddy.model.Location;
import com.ph.walkBuddy.repository.LocationRepository;
import com.ph.walkBuddy.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class LocationServiceTest {

    private LocationRepository locationRepository;
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        locationRepository = mock(LocationRepository.class);
        locationService = new LocationService(locationRepository);
    }

    @Test
    void testGetLocationById() {
        Location loc = new Location("Field", "green.field.trees", "Grassy area", "Can get muddy");
        loc.setId(1L);

        when(locationRepository.findById(1L)).thenReturn(Optional.of(loc));

        Location result = locationService.getLocationById(1L);

        assertThat(result.getName()).isEqualTo("Field");
        verify(locationRepository).findById(1L);
    }
}
