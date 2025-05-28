package com.ph.walkBuddy.controller;

import com.ph.walkBuddy.dto.NewDogRequest;
import com.ph.walkBuddy.model.Dog;
import com.ph.walkBuddy.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dogs")
@CrossOrigin(origins = "http://localhost:3000")
public class DogController {

    private final DogService dogService;

    @Autowired
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    // GET /dogs
    @GetMapping
    public List<Dog> getAllDogs() {
        return dogService.getAllDogs();
    }

    // GET /dogs/{id}
    @GetMapping("/{id}")
    public Dog getDogById(@PathVariable Long id) {
        return dogService.getDogById(id);
    }

    // POST /dogs
    @PostMapping
    public Dog createDog(@RequestBody NewDogRequest dog) {
        return dogService.createDog(dog);
    }

    // PUT /dogs/{id}
    @PutMapping("/{id}")
    public Dog updateDog(@PathVariable Long id, @RequestBody Dog updatedDog) {
        return dogService.updateDog(id, updatedDog);
    }

    // DELETE /dogs/{id}
    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable Long id) {
        dogService.deleteDog(id);
    }
}
