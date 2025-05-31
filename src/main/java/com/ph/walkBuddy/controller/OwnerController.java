package com.ph.walkBuddy.controller;


import com.ph.walkBuddy.dto.NewOwnerRequest;
import com.ph.walkBuddy.dto.OwnerDTO;
import com.ph.walkBuddy.model.Dog;
import com.ph.walkBuddy.model.Owner;
import com.ph.walkBuddy.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
@CrossOrigin(origins = "http://localhost:3000")
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // GET /owners - retrieve all
    @GetMapping
    public List<OwnerDTO> getAllOwners() {
        return ownerService.getAllOwners();
    }

    // GET /owners/{id} - retrieve one
    @GetMapping("/{id}")
    public OwnerDTO getOwnerById(@PathVariable Long id) {
        return ownerService.getOwnerById(id);
    }

    // POST /owners - create new
    @PostMapping
    public OwnerDTO createOwner(@RequestBody NewOwnerRequest req) {
        return ownerService.createOwner(req);
    }

    // PUT /owners/{id} - update      // TODO - convert to DTO
    @PutMapping("/{id}")
    public OwnerDTO updateOwner(@PathVariable Long id, @RequestBody OwnerDTO updatedOwner) {
        return ownerService.updateOwner(id, updatedOwner);
    }

    // DELETE /owners/{id} - delete
    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
    }

    // Add a dog
//    @PostMapping("/owners/{ownerId}/dogs")
//    public Owner addDog(@PathVariable Long ownerId, @RequestBody Dog newDog) {
//        return ownerService.addDogToOwner(ownerId, newDog);
//    }

    // Remove a dog
//    @DeleteMapping("/owners/{ownerId}/dogs/{dogId}")
//    public Owner removeDog(@PathVariable Long ownerId, @PathVariable Long dogId) {
//        return ownerService.removeDogFromOwner(ownerId, dogId);
//    }

}
