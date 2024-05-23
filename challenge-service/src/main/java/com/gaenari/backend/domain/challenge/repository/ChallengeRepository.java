package com.gaenari.backend.domain.challenge.repository;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import com.gaenari.backend.domain.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    /**
     * 주어진 ID 목록을 제외한 도전 과제 엔티티를 조회합니다.
     *
     * @param challengeIds 제외할 도전 과제 ID 목록
     * @return 제외된 도전 과제 엔티티 목록
     */
    List<Challenge> findByIdNotIn(List<Integer> challengeIds);

    /**
     * 도전 과제 ID로 도전 과제를 조회합니다.
     *
     * @param challengeId 도전 과제 ID
     * @return 도전 과제 엔티티
     */
    Challenge findById(Integer challengeId);

    /**
     * 도전 과제 카테고리에 해당하는 도전 과제 엔티티를 조회합니다.
     *
     * @param challengeCategory 도전 과제 카테고리
     * @return 해당 카테고리에 속하는 도전 과제 엔티티 목록
     */
    List<Challenge> findByCategory(ChallengeCategory challengeCategory);
}