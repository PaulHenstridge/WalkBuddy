package com.ph.walkBuddy;

import com.ph.walkBuddy.model.*;
import com.ph.walkBuddy.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WalkRepositoryTest {

    @Autowired
    private WalkRepository walkRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void testCreateWalkWithDogsAndLocation() {
        // create Owner and Dog
        Owner owner = new Owner();
        owner.setName("Jess");
        owner = ownerRepository.save(owner);

        Dog dog1 = new Dog();
        dog1.setName("Finn");
        dog1.setBreed("Labrador");
        dog1.setOwner(owner);
        dog1 = dogRepository.save(dog1);

        // create Location
        Location location = new Location();
        location.setName("Meadow Park");
        location.setW3wLocation("meadow.sunshine.path");
        location = locationRepository.save(location);

        // create Walk
        Walk walk = new Walk();
        walk.setDateTime(LocalDateTime.now());
        walk.setLocation(location);
        walk.setDogs(List.of(dog1));

        Walk saved = walkRepository.save(walk);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getDogs()).hasSize(1);
        assertThat(saved.getLocation().getName()).isEqualTo("Meadow Park");
    }
}
