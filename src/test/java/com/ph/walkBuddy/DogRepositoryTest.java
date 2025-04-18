package com.ph.walkBuddy;

import com.ph.walkBuddy.model.Dog;
import com.ph.walkBuddy.model.Owner;
import com.ph.walkBuddy.repository.DogRepository;
import com.ph.walkBuddy.repository.OwnerRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DogRepositoryTest {

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    public void testSaveDogWithOwner() {
        Owner owner = new Owner();
        owner.setName("Sam Peterson");
        owner = ownerRepository.save(owner);

        Dog dog = new Dog();
        dog.setName("Bobby");
        dog.setBreed("Collie");
        dog.setOwner(owner);

        Dog savedDog = dogRepository.save(dog);

        assertThat(savedDog.getId()).isNotNull();
        assertThat(savedDog.getOwner().getName()).isEqualTo("Sam Peterson");
    }
}
