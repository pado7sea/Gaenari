package com.gaenari.backend.domain.recordChallenge.repository;

import com.gaenari.backend.domain.recordChallenge.entity.RecordChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordChallengeRepository  extends JpaRepository<RecordChallenge, Long> {
}
