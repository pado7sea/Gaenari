package com.gaenari.backend.domain.challenge.repository;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import com.gaenari.backend.domain.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    // 주어진 ID 목록을 제외한 도전 과제 엔티티를 조회
    List<Challenge> findByIdNotIn(List<Integer> challengeIds);

    Challenge findById(Integer challengeId);

}
