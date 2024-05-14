package com.simba.libraryapi.domain.repository;

import com.simba.libraryapi.domain.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
    Patron findPatronById(Long id);

    Optional<Patron> findPatronByEmailAddress(String emailAddress);
}
