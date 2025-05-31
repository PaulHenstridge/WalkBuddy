package com.ph.walkBuddy.service;

import com.ph.walkBuddy.dto.DogDTO;
import com.ph.walkBuddy.dto.NewOwnerRequest;
import com.ph.walkBuddy.dto.OwnerDTO;
import com.ph.walkBuddy.dto.OwnerSummary;
import com.ph.walkBuddy.model.Dog;

import com.ph.walkBuddy.model.Owner;
import com.ph.walkBuddy.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    // Get all owners
    public List<OwnerDTO> getAllOwners() {

        return ownerRepository.findAll().stream()
                .map(this::toOwnerDTO)
                .collect(Collectors.toList());
    }

    // Get single owner
    public OwnerDTO getOwnerById(Long id) {
       Owner owner = ownerRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + id));
        return toOwnerDTO(owner);
    }

    // Create new owner
    public OwnerDTO createOwner(NewOwnerRequest req) {
        Owner o = new Owner();
        o.setName(req.getName());
        o.setContactDetails(req.getContactDetails());
        o.setNotes(req.getNotes());

        Owner saved = ownerRepository.save(o);

        return toOwnerDTO(saved);
    }

    // Update existing owner
    public OwnerDTO updateOwner(Long ownerId, OwnerDTO updatedOwner) {
        Owner existingOwner = fetchOwnerEntity(ownerId);
        existingOwner.setName(updatedOwner.getName());
        existingOwner.setContactDetails(updatedOwner.getContactDetails());
        existingOwner.setNotes(updatedOwner.getNotes());
        Owner savedOwner = ownerRepository.save(existingOwner);
        return toOwnerDTO(savedOwner);
    }

    // Delete owner
    public void deleteOwner(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new EntityNotFoundException("Owner not found with id: " + id);
        }
        ownerRepository.deleteById(id);
    }

    private OwnerDTO toOwnerDTO(Owner o) {
        OwnerDTO dto = new OwnerDTO();
        dto.setId(o.getId());
        dto.setName(o.getName());
        dto.setContactDetails(o.getContactDetails());
        dto.setNotes(o.getNotes());

        // Map each Dog to DogDTO
        List<DogDTO> dogDTOs = o.getDogs().stream()
                .map(d -> {
                    DogDTO dd = new DogDTO();
                    dd.setId(d.getId());
                    dd.setName(d.getName());
                    dd.setBreed(d.getBreed());
                    dd.setDescription(d.getDescription());
                    dd.setNotes(d.getNotes());
                    // owner summary inside the dog
                    dd.setOwner(new OwnerSummary(o.getId(), o.getName()));
                    return dd;
                }).collect(Collectors.toList());

        dto.setDogs(dogDTOs);
        return dto;
    }

    private Owner fetchOwnerEntity(Long id){
        return ownerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + id));
    }
}
