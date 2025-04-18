package com.ph.walkBuddy;

import com.ph.walkBuddy.model.Owner;
import com.ph.walkBuddy.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // uses in-memory db, loads only JPA layer
public class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    public void testCreateAndFindOwner() {
        Owner owner = new Owner();
        owner.setName("Sarah Jones");
        owner.setNotes("Prefers weekend walks");

        ownerRepository.save(owner);

        Optional<Owner> found = ownerRepository.findById(owner.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Sarah Jones");
    }
}
