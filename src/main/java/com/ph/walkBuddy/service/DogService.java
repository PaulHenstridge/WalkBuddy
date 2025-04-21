package com.ph.walkBuddy.service;

import com.ph.walkBuddy.model.Dog;
import com.ph.walkBuddy.repository.DogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogService {

    private final DogRepository dogRepository;

    @Autowired
    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    public Dog getDogById(Long id) {
        return dogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dog not found with id: " + id));
    }

    public List<Dog> getDogsByOwner(Long ownerId) {
        return dogRepository.findByOwnerId(ownerId);
    }

    public Dog createDog(Dog dog) {
        return dogRepository.save(dog);
    }

    public Dog updateDog(Long id, Dog updatedDog) {
        Dog existingDog = getDogById(id);
        existingDog.setName(updatedDog.getName());
        existingDog.setBreed(updatedDog.getBreed());
        existingDog.setDescription(updatedDog.getDescription());
        existingDog.setNotes(updatedDog.getNotes());


        return dogRepository.save(existingDog);
    }

    public void deleteDog(Long id) {
        if (!dogRepository.existsById(id)) {
            throw new EntityNotFoundException("Dog not found with id: " + id);
        }
        dogRepository.deleteById(id);
    }
}

