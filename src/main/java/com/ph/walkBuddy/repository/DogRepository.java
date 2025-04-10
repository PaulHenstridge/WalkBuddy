package com.ph.walkBuddy.repository;

import com.ph.walkBuddy.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Long> {
    List<Dog> findByOwnerId(Long ownerId);
}