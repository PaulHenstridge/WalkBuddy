package com.ph.walkBuddy;

import com.ph.walkBuddy.model.ContactDetails;
import com.ph.walkBuddy.model.Dog;
import com.ph.walkBuddy.model.Owner;
import com.ph.walkBuddy.repository.OwnerRepository;
import com.ph.walkBuddy.service.OwnerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class OwnerServiceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerService ownerService;

    private Owner savedOwner;

    @BeforeEach
    void setUp() {
        ContactDetails contactDetails = new ContactDetails(); // Assume default constructor exists
        Owner owner = new Owner("Jane", contactDetails, "Loves dogs");
        savedOwner = ownerRepository.save(owner);
    }

    @Test
    void testCreateOwner() {
        Owner newOwner = new Owner("Bob", new ContactDetails(), "New note");

        Owner saved = ownerService.createOwner(newOwner);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Bob");
    }

    @Test
    void testGetAllOwners() {
        List<Owner> owners = ownerService.getAllOwners();

        assertThat(owners).isNotEmpty();
        assertThat(owners).extracting(Owner::getName).contains("Jane");
    }

    @Test
    void testGetOwnerById() {
        Owner found = ownerService.getOwnerById(savedOwner.getId());

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Jane");
    }

    @Test
    void testUpdateOwner() {
        Owner updates = new Owner("Updated Jane", new ContactDetails(), "Updated notes");

        Owner updated = ownerService.updateOwner(savedOwner.getId(), updates);

        assertThat(updated.getName()).isEqualTo("Updated Jane");
        assertThat(updated.getNotes()).isEqualTo("Updated notes");
    }

    @Test
    void testDeleteOwner() {
        ownerService.deleteOwner(savedOwner.getId());

        assertThatThrownBy(() -> ownerService.getOwnerById(savedOwner.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testAddDogToOwner() {
        Dog newDog = new Dog("Fido", "Labrador", "Friendly", "Loves to swim", null);

        Owner updatedOwner = ownerService.addDogToOwner(savedOwner.getId(), newDog);

        assertThat(updatedOwner.getDogs()).isNotEmpty();
        assertThat(updatedOwner.getDogs().get(0).getName()).isEqualTo("Fido");
    }

    @Test
    void testRemoveDogFromOwner() {
        Dog dog = new Dog("Buddy", "Collie", "Smart dog", "Herder", savedOwner);
        savedOwner.getDogs().add(dog);
        ownerRepository.save(savedOwner);

        Dog existingDog = savedOwner.getDogs().get(0);

        Owner updatedOwner = ownerService.removeDogFromOwner(savedOwner.getId(), existingDog.getId());

        assertThat(updatedOwner.getDogs()).isEmpty();
    }

    @Test
    void testGetOwnerByIdThrowsIfNotFound() {
        assertThatThrownBy(() -> ownerService.getOwnerById(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Owner not found");
    }

    @Test
    void testDeleteOwnerThrowsIfNotFound() {
        assertThatThrownBy(() -> ownerService.deleteOwner(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Owner not found");
    }
}
