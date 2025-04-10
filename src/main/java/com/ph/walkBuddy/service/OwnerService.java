package com.ph.walkBuddy.service;

import com.ph.walkBuddy.model.Dog;
import com.ph.walkBuddy.model.Owner;
import com.ph.walkBuddy.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    // Get all owners
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    // Get single owner
    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + id));
    }

    // Create new owner
    public Owner createOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    // Update existing owner
    public Owner updateOwner(Long id, Owner updatedOwner) {
        Owner existingOwner = getOwnerById(id);
        existingOwner.setName(updatedOwner.getName());
        existingOwner.setContactDetails(updatedOwner.getContactDetails());
        existingOwner.setNotes(updatedOwner.getNotes());
        return ownerRepository.save(existingOwner);
    }

    public Owner addDogToOwner(Long ownerId, Dog newDog) {
        Owner owner = getOwnerById(ownerId);

        newDog.setOwner(owner);
        owner.getDogs().add(newDog);

        return ownerRepository.save(owner);
    }

    public Owner removeDogFromOwner(Long ownerId, Long dogId) {
        Owner owner = getOwnerById(ownerId);

        Dog dogToRemove = owner.getDogs().stream()
                .filter(d -> d.getId().equals(dogId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Dog not found for this owner"));

        owner.getDogs().remove(dogToRemove);
        dogToRemove.setOwner(null);

        return ownerRepository.save(owner);
    }



    // Delete owner
    public void deleteOwner(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new EntityNotFoundException("Owner not found with id: " + id);
        }
        ownerRepository.deleteById(id);
    }
}
