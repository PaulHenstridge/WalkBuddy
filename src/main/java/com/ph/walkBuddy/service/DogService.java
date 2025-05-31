package com.ph.walkBuddy.service;

import com.ph.walkBuddy.dto.DogDTO;
import com.ph.walkBuddy.dto.NewDogRequest;
import com.ph.walkBuddy.dto.OwnerSummary;
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
import java.util.stream.Collectors;

@Service
public class DogService {

    private final DogRepository dogRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public DogService(DogRepository dogRepository, OwnerRepository ownerRepository) {

        this.dogRepository = dogRepository;
        this.ownerRepository = ownerRepository;
    }

    public List<DogDTO> getAllDogs() {

        return dogRepository.findAll().stream()
                .map(this::toDogDTO)
                .collect(Collectors.toList());
    }

    public DogDTO getDogById(Long id) {
        Dog dog = dogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dog not found with id: " + id));
        return toDogDTO(dog);
    }

    //
    public List<DogDTO> getDogsByOwner(Long ownerId) {

        return dogRepository.findByOwnerId(ownerId).stream()
                .map(this::toDogDTO)
                .collect(Collectors.toList());
    }

    public DogDTO createDog(NewDogRequest req) {
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

        Dog saved = dogRepository.save(dog);

        return toDogDTO(saved);
    }

    public DogDTO updateDog(Long id, DogDTO updatedDog) {
        Dog existingDog = fetchDogEntity(id);
        existingDog.setName(updatedDog.getName());
        existingDog.setBreed(updatedDog.getBreed());
        existingDog.setDescription(updatedDog.getDescription());
        existingDog.setNotes(updatedDog.getNotes());


        Dog saved = dogRepository.save(existingDog);

        return toDogDTO(saved);
    }

    public void deleteDog(Long id) {
        if (!dogRepository.existsById(id)) {
            throw new EntityNotFoundException("Dog not found with id: " + id);
        }
        dogRepository.deleteById(id);
    }

    public Dog addRatingToDog(Long dogId, DogRating rating) {
        Dog dog = fetchDogEntity(dogId);
        rating.setDog(dog);
        dog.getDogRatings().add(rating);
        return dogRepository.save(dog);
    }

    public Dog addReportToDog(Long dogId, DogReport report) {
        Dog dog = fetchDogEntity(dogId);
        report.setDog(dog);
        dog.getDogReports().add(report);
        return dogRepository.save(dog);
    }

    private DogDTO toDogDTO(Dog d) {
        DogDTO dto = new DogDTO();
        dto.setId(d.getId());
        dto.setName(d.getName());
        dto.setBreed(d.getBreed());
        dto.setDescription(d.getDescription());
        dto.setNotes(d.getNotes());
        dto.setOwner(new OwnerSummary(
                d.getOwner().getId(), d.getOwner().getName()));
        return dto;
    }

    private Dog fetchDogEntity(Long id){
        return dogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dog not found with id: " + id));
    }

}

