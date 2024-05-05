package com.gaenari.backend.domain.mypet.repository;

import com.gaenari.backend.domain.mypet.entity.MyPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyPetRepository extends JpaRepository<MyPet, Long> {
    List<MyPet> findByMemberId(Long memberId);
    Optional<MyPet> findByMemberIdAndIsPartner(Long memberId, Boolean isPartner);
    Optional<MyPet> findByMemberIdAndDogId(Long memberId, Long dogId);
}
