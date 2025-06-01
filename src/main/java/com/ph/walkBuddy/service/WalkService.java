package com.ph.walkBuddy.service;

import com.ph.walkBuddy.dto.NewWalkRequest;
import com.ph.walkBuddy.dto.WalkDTO;
import com.ph.walkBuddy.model.*;
import com.ph.walkBuddy.repository.WalkRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalkService {

    private final WalkRepository walkRepository;
    private final DogService dogService;

    @Autowired
    public WalkService(WalkRepository walkRepository, DogService dogService) {
        this.walkRepository = walkRepository;
        this.dogService = dogService;

    }

    public WalkDTO createWalk(NewWalkRequest req) {

        return walkRepository.save(req);
    }

    public Walk getWalkById(Long id) {
        return walkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Walk not found with id: " + id));
    }

    public List<Walk> getAllWalks() {
        return walkRepository.findAll();
    }

    public void deleteWalk(Long id) {
        if (!walkRepository.existsById(id)) {
            throw new EntityNotFoundException("Walk not found with id: " + id);
        }
        walkRepository.deleteById(id);
    }

    public Walk addDogToWalk(Long walkId, Dog dog) {
        Walk walk = getWalkById(walkId);
        if (walk.isComplete()) {
            throw new IllegalStateException("Cannot add dogs to a completed walk");
        }
        walk.getDogs().add(dog);
        return walkRepository.save(walk);
    }

    public Walk updateWalkDateTime(Long walkId, LocalDateTime newDateTime) {
        Walk walk = getWalkById(walkId);
        if (walk.isComplete()) {
            throw new IllegalStateException("Cannot update date/time of a completed walk");
        }
        walk.setDateTime(newDateTime);
        return walkRepository.save(walk);
    }

    public Walk addRatingToWalk(Long walkId, WalkRating rating) {
        Walk walk = getWalkById(walkId);
        if (!walk.isComplete()) {
            throw new IllegalStateException("Cannot rate a walk that is not completed");
        }
        rating.setWalk(walk);
        walk.setRating(rating);
        return walkRepository.save(walk);
    }

    public Walk addReportToWalk(Long walkId, WalkReport report) {
        Walk walk = getWalkById(walkId);
        if (!walk.isComplete()) {
            throw new IllegalStateException("Cannot add a report to an incomplete walk");
        }
        report.setWalk(walk);
        walk.setReport(report);
        return walkRepository.save(walk);
    }


    public Walk markWalkAsComplete(Long walkId) {
        Walk walk = getWalkById(walkId);
        walk.setComplete(true);
        return walkRepository.save(walk);
    }

    private WalkDTO toWalkDTO(Walk w) {
        WalkDTO dto = new WalkDTO();
        dto.setId(w.getId());
        dto.setDateTime(w.getDateTime());
        dto.setLocation(w.getLocation());
        dto.setDogs(w.getDogs().stream()
                .map(dogService::toDogDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private Dog fetchDogEntity(Long id){
        return dogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dog not found with id: " + id));
    }
}
