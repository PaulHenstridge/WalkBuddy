package com.ph.walkBuddy.service;

import com.ph.walkBuddy.dto.NewWalkRequest;
import com.ph.walkBuddy.dto.WalkDTO;
import com.ph.walkBuddy.model.*;
import com.ph.walkBuddy.repository.DogRepository;
import com.ph.walkBuddy.repository.LocationRepository;
import com.ph.walkBuddy.repository.WalkRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalkService {

    private final WalkRepository walkRepository;
    private final LocationRepository locationRepository;
    private final DogRepository dogRepository;

    // TODO - move toDogDTO etc to a separate mapper file, so not bringing in whole dogService.  repeat for Dog, owner.
    private final DogService dogService;

    @Autowired
    public WalkService(WalkRepository walkRepository, LocationRepository locationRepository, DogRepository dogRepository, DogService dogService) {
        this.walkRepository = walkRepository;
        this.dogService = dogService;
        this.locationRepository = locationRepository;
        this.dogRepository = dogRepository;


    }

    public WalkDTO createWalk(NewWalkRequest req) {
        // 1) Look up related entities first:
        Location loc = locationRepository.findById(req.getLocationId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Location not found"));

        List<Dog> dogs = dogRepository.findAllById(req.getDogIds());
        if (dogs.size() != req.getDogIds().size()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "One or more dogs not found");
        }

        // 2) Build a new Walk entity from the request
        Walk w = new Walk();
        w.setDateTime(req.getDateTime());
        w.setLocation(loc);
        w.setDogs(dogs);

        // 3) Save the entity
        Walk saved = walkRepository.save(w);

        // 4) Convert saved entity to DTO and return
        return toWalkDTO(saved);
    }


    public WalkDTO getWalkById(Long id) {
        Walk saved = walkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Walk not found with id: " + id));
        return toWalkDTO(saved);
    }

    public List<WalkDTO> getAllWalks() {
        return walkRepository.findAll().stream()
                .map(this::toWalkDTO)
                .collect(Collectors.toList());
    }

    public void deleteWalk(Long id) {
        if (!walkRepository.existsById(id)) {
            throw new EntityNotFoundException("Walk not found with id: " + id);
        }
        walkRepository.deleteById(id);
    }

    public WalkDTO addDogToWalk(Long walkId, Long dogId ) {
        Walk walk = fetchWalkEntity(walkId);
        Dog dog = dogService.fetchDogEntity(dogId);
        if (walk.isComplete()) {
            throw new IllegalStateException("Cannot add dogs to a completed walk");
        }
        walk.getDogs().add(dog);
        Walk saved = walkRepository.save(walk);
        return toWalkDTO(saved);
    }

    public WalkDTO updateWalkDateTime(Long walkId, LocalDateTime newDateTime) {
        Walk walk = fetchWalkEntity(walkId);
        if (walk.isComplete()) {
            throw new IllegalStateException("Cannot update date/time of a completed walk");
        }
        walk.setDateTime(newDateTime);
        Walk saved = walkRepository.save(walk);

        return toWalkDTO(saved);
    }

    public WalkDTO addRatingToWalk(Long walkId, WalkRating rating) {
        Walk walk = fetchWalkEntity(walkId);
        if (!walk.isComplete()) {
            throw new IllegalStateException("Cannot rate a walk that is not completed");
        }
        rating.setWalk(walk);
        walk.setRating(rating);
        Walk saved = walkRepository.save(walk);

        return toWalkDTO(saved);
    }

    public WalkDTO addReportToWalk(Long walkId, String reportString) {
        Walk walk = fetchWalkEntity(walkId);
        if (!walk.isComplete()) {
            throw new IllegalStateException("Cannot add a report to an incomplete walk");
        }
        WalkReport report = new WalkReport(walk, reportString);

        report.setWalk(walk);
        walk.setReport(report);
        Walk saved = walkRepository.save(walk);

        return toWalkDTO(saved);
    }


    public WalkDTO markWalkAsComplete(Long walkId) {
        Walk walk = fetchWalkEntity(walkId);
        walk.setComplete(true);
        Walk saved = walkRepository.save(walk);
        System.out.println("✅ Walk complete status after save: " + saved.isComplete());
        return toWalkDTO(saved);
    }

    private WalkDTO toWalkDTO(Walk w) {

        Long locationId = (w.getLocation() != null) ? w.getLocation().getId() : null;
        String locationName = (w.getLocation() != null) ? w.getLocation().getName() : null;
        String report   = (w.getReport() != null) ? w.getReport().getNotes()   : null;

        var dogs = w.getDogs().stream()
                .map(dogService::toDogDTO)
                .toList();


        return new WalkDTO(
                w.getId(),
                locationId,
                locationName,
                w.getDateTime(),
                dogs,
                w.isComplete(),
                report
        );
    }

    private Walk fetchWalkEntity(Long id){
        return walkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Walk not found with id: " + id));
    }
}
