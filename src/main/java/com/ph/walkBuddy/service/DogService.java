package com.ph.walkBuddy.service;

import com.ph.walkBuddy.dto.NewDogRequest;
import com.ph.walkBuddy.model.Dog;
import com.ph.walkBuddy.model.DogRating;
import com.ph.walkBuddy.model.DogReport;
import com.ph.walkBuddy.model.Owner;
import com.ph.walkBuddy.repository.DogRepository;
import com.ph.walkBuddy.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DogService {

    private final DogRepository dogRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public DogService(DogRepository dogRepository, OwnerRepository ownerRepository) {

        this.dogRepository = dogRepository;
        this.ownerRepository = ownerRepository;
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

    public Dog createDog(NewDogRequest req) {
        // 1) Look up the Owner by ID
        Owner owner = ownerRepository.findById(req.getOwnerId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found"));

        // 2) Create and populate Dog entity
        Dog dog = new Dog();
        dog.setName(req.getName());
        dog.setBreed(req.getBreed());
        dog.setDescription(req.getDescription());
        dog.setNotes(req.getNotes());
        dog.setOwner(owner);

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

    public Dog addRatingToDog(Long dogId, DogRating rating) {
        Dog dog = getDogById(dogId);
        rating.setDog(dog);
        dog.getDogRatings().add(rating);
        return dogRepository.save(dog);
    }

    public Dog addReportToDog(Long dogId, DogReport report) {
        Dog dog = getDogById(dogId);
        report.setDog(dog);
        dog.getDogReports().add(report);
        return dogRepository.save(dog);
    }


}

