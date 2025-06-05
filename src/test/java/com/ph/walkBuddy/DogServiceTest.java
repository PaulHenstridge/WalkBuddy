package com.ph.walkBuddy;

import com.ph.walkBuddy.dto.DogDTO;
import com.ph.walkBuddy.dto.NewDogRequest;
import com.ph.walkBuddy.enums.RatingLevel;
import com.ph.walkBuddy.model.*;
import com.ph.walkBuddy.repository.DogRepository;
import com.ph.walkBuddy.repository.LocationRepository;
import com.ph.walkBuddy.repository.OwnerRepository;
import com.ph.walkBuddy.repository.WalkRepository;
import com.ph.walkBuddy.service.DogService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class DogServiceTest {

    @Autowired
    private DogService dogService;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private WalkRepository walkRepository;

    @Autowired
    private LocationRepository locationRepository;

    private Owner savedOwner;

    @BeforeEach
    void setUp() {
        ContactDetails contactDetails = new ContactDetails();
        Owner owner = new Owner("Jane", contactDetails, "Loves dogs");
        savedOwner = ownerRepository.save(owner);
    }
    @Test
    void testCreateDog() {

        NewDogRequest newDog = new NewDogRequest("Fido", "Labrador", "Friendly dog", "Loves water", savedOwner.getId());

        DogDTO saved = dogService.createDog(newDog);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Fido");
    }

    @Test
    void testGetAllDogs() {
        Dog d1 = new Dog("Rex", "German Shepherd", "Strong and loyal", "Needs lots of exercise",savedOwner);
        Dog d2 = new Dog("Bella", "Beagle", "Curious", "Can be noisy",savedOwner);

        dogRepository.save(d1);
        dogRepository.save(d2);

        List<DogDTO> dogs = dogService.getAllDogs();

        assertThat(dogs).hasSize(2);
    }

    @Test
    void testGetDogById() {
        Dog dog = new Dog("Max", "Terrier", "Energetic", "Chases cats", savedOwner);
        Dog saved = dogRepository.save(dog);

        DogDTO found = dogService.getDogById(saved.getId());

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Max");
    }

    @Test
    void testUpdateDog() {
        Dog original = new Dog("Buddy", "Spaniel", "Loves walks", "Very friendly", savedOwner);
        Dog saved = dogRepository.save(original);

        Dog updates = new Dog("Buddy Updated", "Springer", "Updated desc", "Still friendly", savedOwner);

        Dog updated = dogService.updateDog(saved.getId(), updates);

        assertThat(updated.getName()).isEqualTo("Buddy Updated");
        assertThat(updated.getDescription()).isEqualTo("Updated desc");
    }

    @Test
    void testDeleteDog() {
        Dog dog = new Dog("Shadow", "Husky", "Quiet", "Needs cold weather", savedOwner);
        Dog saved = dogRepository.save(dog);

        dogService.deleteDog(saved.getId());

        assertThat(dogRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void testGetDogByIdThrowsIfNotFound() {
        Long invalidId = 999L;

        assertThatThrownBy(() -> dogService.getDogById(invalidId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Dog not found");
    }

    @Test
    void testDeleteDogThrowsIfNotFound() {
        Long invalidId = 888L;

        assertThatThrownBy(() -> dogService.deleteDog(invalidId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Dog not found");
    }

    // Dog Ratings and Reports

    @Test
    void testAddDogRatingWithoutWalk_success() {
        Dog dog = new Dog("Shadow", "Husky", "Quiet", "Needs cold weather", savedOwner);
        Dog savedDog = dogRepository.save(dog);
        DogRating rating = new DogRating(savedDog, null, RatingLevel.GREAT);

        Dog updated = dogService.addRatingToDog(savedDog.getId(), rating);

        assertThat(updated.getDogRatings())
                .extracting(DogRating::getRating)
                .contains(RatingLevel.GREAT);    }

    @Test
    void testAddDogRatingWithWalk_success() {
        Dog dog = new Dog("Shadow", "Husky", "Quiet", "Needs cold weather", savedOwner);
        Dog savedDog = dogRepository.save(dog);

        Location location = new Location("Park", "green.grass.sun", "A big park", "Watch out for geese");
        Location savedLocation = locationRepository.save(location);

        Walk walk = walkRepository.save(new Walk(LocalDateTime.now(), savedLocation));
        DogRating rating = new DogRating(savedDog, walk, RatingLevel.OK);

        Dog updated = dogService.addRatingToDog(savedDog.getId(), rating);

        assertThat(updated.getDogRatings())
                .extracting(DogRating::getRating)
                .contains(RatingLevel.OK);

        assertThat(updated.getDogRatings())
                .anySatisfy(r -> assertThat(r.getWalk().getId()).isEqualTo(walk.getId()));
    }

    @Test
    void testAddDogReportWithoutWalk_success() {
        Dog dog = new Dog("Shadow", "Husky", "Quiet", "Needs cold weather", savedOwner);
        Dog savedDog = dogRepository.save(dog);

        DogReport report = new DogReport(savedDog, null, "Did well today.");

        Dog updated = dogService.addReportToDog(savedDog.getId(), report);

        assertThat(updated.getDogReports())
                .extracting(DogReport::getNotes)
                .contains("Did well today.");

        assertThat(report.getNotes()).isEqualTo("Did well today.");
    }

    @Test
    void testAddDogReportWithWalk_success() {
        Dog dog = new Dog("Shadow", "Husky", "Quiet", "Needs cold weather", savedOwner);
        Dog savedDog = dogRepository.save(dog);

        Location location = new Location("Park", "green.grass.sun", "A big park", "Watch out for geese");
        Location savedLocation = locationRepository.save(location);

        Walk walk = walkRepository.save(new Walk(LocalDateTime.now(), savedLocation));
        DogReport report = new DogReport(savedDog, walk, "Pulled on the lead.");

        Dog updated = dogService.addReportToDog(savedDog.getId(), report);

        assertThat(updated.getDogReports())
                .extracting(DogReport::getNotes)
                .contains("Pulled on the lead.");

        assertThat(report.getWalk()).isEqualTo(walk);
    }

}
