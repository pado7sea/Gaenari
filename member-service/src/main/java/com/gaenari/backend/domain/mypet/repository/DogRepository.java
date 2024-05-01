package com.gaenari.backend.domain.mypet.repository;

import com.gaenari.backend.domain.mypet.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    Optional<Dog> findById(Long id);
}
