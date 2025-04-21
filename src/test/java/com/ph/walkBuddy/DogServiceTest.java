package com.ph.walkBuddy;

import com.ph.walkBuddy.model.ContactDetails;
import com.ph.walkBuddy.model.Dog;
import com.ph.walkBuddy.model.Owner;
import com.ph.walkBuddy.repository.DogRepository;
import com.ph.walkBuddy.repository.OwnerRepository;
import com.ph.walkBuddy.service.DogService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

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

    private Owner savedOwner;

    @BeforeEach
    void setUp() {
        ContactDetails contactDetails = new ContactDetails();
        Owner owner = new Owner("Jane", contactDetails, "Loves dogs");
        savedOwner = ownerRepository.save(owner);
    }
    @Test
    void testCreateDog() {

        Dog dog = new Dog("Fido", "Labrador", "Friendly dog", "Loves water", savedOwner);

        Dog saved = dogService.createDog(dog);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Fido");
    }

    @Test
    void testGetAllDogs() {
        Dog d1 = new Dog("Rex", "German Shepherd", "Strong and loyal", "Needs lots of exercise",savedOwner);
        Dog d2 = new Dog("Bella", "Beagle", "Curious", "Can be noisy",savedOwner);

        dogRepository.save(d1);
        dogRepository.save(d2);

        List<Dog> dogs = dogService.getAllDogs();

        assertThat(dogs).hasSize(2);
    }

    @Test
    void testGetDogById() {
        Dog dog = new Dog("Max", "Terrier", "Energetic", "Chases cats", savedOwner);
        Dog saved = dogRepository.save(dog);

        Dog found = dogService.getDogById(saved.getId());

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
}
