package com.ph.walkBuddy.controller;


import com.ph.walkBuddy.model.Dog;
import com.ph.walkBuddy.model.Owner;
import com.ph.walkBuddy.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // GET /owners - retrieve all
    @GetMapping
    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }

    // GET /owners/{id} - retrieve one
    @GetMapping("/{id}")
    public Owner getOwnerById(@PathVariable Long id) {
        return ownerService.getOwnerById(id);
    }

    // POST /owners - create new
    @PostMapping
    public Owner createOwner(@RequestBody Owner owner) {
        return ownerService.createOwner(owner);
    }

    // PUT /owners/{id} - update
    @PutMapping("/{id}")
    public Owner updateOwner(@PathVariable Long id, @RequestBody Owner updatedOwner) {
        return ownerService.updateOwner(id, updatedOwner);
    }

    // DELETE /owners/{id} - delete
    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
    }

    @PostMapping("/owners/{ownerId}/dogs")
    public Owner addDog(@PathVariable Long ownerId, @RequestBody Dog newDog) {
        return ownerService.addDogToOwner(ownerId, newDog);
    }

    @DeleteMapping("/owners/{ownerId}/dogs/{dogId}")
    public Owner removeDog(@PathVariable Long ownerId, @PathVariable Long dogId) {
        return ownerService.removeDogFromOwner(ownerId, dogId);
    }

}
