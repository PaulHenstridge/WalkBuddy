package com.ph.walkBuddy;

import com.ph.walkBuddy.enums.RatingLevel;
import com.ph.walkBuddy.model.*;
import com.ph.walkBuddy.repository.*;
import com.ph.walkBuddy.service.WalkService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

        @SpringBootTest
        @Transactional
        public class WalkServiceTest {

            @Autowired
            private WalkService walkService;
            @Autowired
            private WalkRepository walkRepository;
            @Autowired
            private LocationRepository locationRepository;
            @Autowired
            private DogRepository dogRepository;
            @Autowired
            private OwnerRepository ownerRepository;

            private Location savedLocation;
            private Dog savedDog;
            private Owner savedOwner;

            @BeforeEach
            void setUp() {
                Location location = new Location("Park", "park.green.tree", "A local park", "Busy on weekends");
                savedLocation = locationRepository.save(location);

                ContactDetails contactDetails = new ContactDetails(); // Assume default constructor exists
                Owner owner = new Owner("Jane", contactDetails, "Loves dogs");
                savedOwner = ownerRepository.save(owner);

                Dog dog = new Dog("Rex", "Labrador", "Friendly", "Loves water", savedOwner);
                savedDog = dogRepository.save(dog);
            }

            @Test
            void testCreateWalk() {
                Walk walk = new Walk(LocalDateTime.now(), savedLocation);
                Walk saved = walkService.createWalk(walk);

                assertThat(saved.getId()).isNotNull();
                assertThat(saved.getLocation().getId()).isEqualTo(savedLocation.getId());
            }

            @Test
            void testGetWalkById() {
                Walk walk = new Walk(LocalDateTime.now(), savedLocation);
                Walk saved = walkRepository.save(walk);

                Walk found = walkService.getWalkById(saved.getId());

                assertThat(found).isNotNull();
                assertThat(found.getId()).isEqualTo(saved.getId());
            }

            @Test
            void testGetAllWalks() {
                walkRepository.save(new Walk(LocalDateTime.now(), savedLocation));
                walkRepository.save(new Walk(LocalDateTime.now().plusHours(1), savedLocation));

                List<Walk> walks = walkService.getAllWalks();

                assertThat(walks).hasSize(2);
            }

            @Test
            void testDeleteWalk() {
                Walk walk = new Walk(LocalDateTime.now(), savedLocation);
                Walk saved = walkRepository.save(walk);

                walkService.deleteWalk(saved.getId());

                assertThat(walkRepository.findById(saved.getId())).isEmpty();
            }

            @Test
            void testAddDogToIncompleteWalk() {
                Walk walk = walkRepository.save(new Walk(LocalDateTime.now(), savedLocation));

                walkService.addDogToWalk(walk.getId(), savedDog);

                Walk updated = walkRepository.findById(walk.getId()).orElseThrow();
                assertThat(updated.getDogs()).contains(savedDog);
            }

            @Test
            void testAddDogToCompletedWalk_throwsException() {
                Walk walk = walkRepository.save(new Walk(LocalDateTime.now(), savedLocation));
                walk.setComplete(true);
                walkRepository.save(walk);

                assertThatThrownBy(() -> walkService.addDogToWalk(walk.getId(), savedDog))
                        .isInstanceOf(IllegalStateException.class)
                        .hasMessageContaining("completed");
            }

            @Test
            void testUpdateDateTimeOnIncompleteWalk() {
                Walk walk = walkRepository.save(new Walk(LocalDateTime.now(), savedLocation));

                LocalDateTime newDateTime = LocalDateTime.now().plusDays(1);
                walkService.updateWalkDateTime(walk.getId(), newDateTime);

                Walk updated = walkRepository.findById(walk.getId()).orElseThrow();
                assertThat(updated.getDateTime()).isEqualTo(newDateTime);
            }

            @Test
            void testUpdateDateTimeOnCompletedWalk_throwsException() {
                Walk walk = new Walk(LocalDateTime.now(), savedLocation);
                walk.setComplete(true);
                walkRepository.save(walk);

                assertThatThrownBy(() -> walkService.updateWalkDateTime(walk.getId(), LocalDateTime.now().plusDays(1)))
                        .isInstanceOf(IllegalStateException.class)
                        .hasMessageContaining("completed");
            }

            @Test
            void testAddRatingBeforeWalkComplete_throwsException() {
                Walk walk = walkRepository.save(new Walk(LocalDateTime.now(), savedLocation));
                WalkRating rating = new WalkRating(walk, RatingLevel.GREAT);

                assertThatThrownBy(() -> walkService.addRatingToWalk(walk.getId(), rating))
                        .isInstanceOf(IllegalStateException.class)
                        .hasMessageContaining("complete");
            }

            @Test
            void testAddRatingAfterWalkComplete_success() {
                Walk walk = new Walk(LocalDateTime.now(), savedLocation);
                walk.setComplete(true);
                walk = walkRepository.save(walk);

                WalkRating rating = new WalkRating(walk, RatingLevel.GREAT);
                Walk updated = walkService.addRatingToWalk(walk.getId(), rating);

                assertThat(updated.getRating()).isNotNull();
                assertThat(updated.getRating().getRating()).isEqualTo(RatingLevel.GREAT);
                assertThat(updated.getRating().getRatedAt()).isNotNull();

            }

            @Test
            void testAddReportBeforeWalkComplete_throwsException() {
                Walk walk = walkRepository.save(new Walk(LocalDateTime.now(), savedLocation));
                WalkReport report = new WalkReport(walk, "Great walk, saw ducks!");

                assertThatThrownBy(() -> walkService.addReportToWalk(walk.getId(), report))
                        .isInstanceOf(IllegalStateException.class)
                        .hasMessageContaining("incomplete");
            }

            @Test
            void testAddReportAfterWalkComplete_success() {
                Walk walk = new Walk(LocalDateTime.now(), savedLocation);
                walk.setComplete(true);
                walk = walkRepository.save(walk);

                WalkReport report = new WalkReport(walk, "Sunny day, everyone happy!");
                Walk updated = walkService.addReportToWalk(walk.getId(), report);

                assertThat(updated.getReport()).isNotNull();
                assertThat(updated.getReport().getNotes()).isEqualTo("Sunny day, everyone happy!");
                assertThat(updated.getReport().getCreatedAt()).isNotNull();
            }

            @Test
            void testMarkWalkAsComplete() {
                Walk walk = walkRepository.save(new Walk(LocalDateTime.now(), savedLocation));

                walkService.markWalkAsComplete(walk.getId());

                Walk updated = walkRepository.findById(walk.getId()).orElseThrow();
                assertThat(updated.isComplete()).isTrue();
            }
    }