package com.ph.walkBuddy.controller;

import com.ph.walkBuddy.dto.NewWalkRequest;
import com.ph.walkBuddy.model.Dog;
import com.ph.walkBuddy.model.Walk;
import com.ph.walkBuddy.model.WalkRating;
import com.ph.walkBuddy.model.WalkReport;
import com.ph.walkBuddy.service.WalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/walks")
@CrossOrigin(origins = "http://localhost:3000")
public class WalkController {

    private final WalkService walkService;

    @Autowired
    public WalkController(WalkService walkService) {
        this.walkService = walkService;
    }

    @PostMapping
    public Walk createWalk(@RequestBody NewWalkRequest walk) {
        return walkService.createWalk(walk);
    }

    @GetMapping("/{id}")
    public Walk getWalkById(@PathVariable Long id) {
        return walkService.getWalkById(id);
    }

    @GetMapping
    public List<Walk> getAllWalks() {
        return walkService.getAllWalks();
    }

    @DeleteMapping("/{id}")
    public void deleteWalk(@PathVariable Long id) {
        walkService.deleteWalk(id);
    }

    @PostMapping("/{walkId}/dogs")
    public Walk addDogToWalk(@PathVariable Long walkId, @RequestBody Dog dog) {
        return walkService.addDogToWalk(walkId, dog);
    }

    @PatchMapping("/{walkId}/date-time")
    public Walk updateWalkDateTime(@PathVariable Long walkId, @RequestBody LocalDateTime newDateTime) {
        return walkService.updateWalkDateTime(walkId, newDateTime);
    }

    @PostMapping("/{walkId}/rating")
    public Walk addRatingToWalk(@PathVariable Long walkId, @RequestBody WalkRating rating) {
        return walkService.addRatingToWalk(walkId, rating);
    }

    @PostMapping("/{walkId}/report")
    public Walk addReportToWalk(@PathVariable Long walkId, @RequestBody WalkReport report) {
        return walkService.addReportToWalk(walkId, report);
    }

    @PatchMapping("/{walkId}/complete")
    public Walk markWalkAsComplete(@PathVariable Long walkId) {
        return walkService.markWalkAsComplete(walkId);
    }

}
