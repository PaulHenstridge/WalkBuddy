package com.ph.walkBuddy.repository;

import com.ph.walkBuddy.model.Location;
import com.ph.walkBuddy.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

}