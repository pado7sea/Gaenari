package com.gaenari.backend.domain.challenge.service.impl;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeAllTypes;
import com.gaenari.backend.domain.challenge.dto.responseDto.ChallengeDto;
import com.gaenari.backend.domain.challenge.entity.Challenge;
import com.gaenari.backend.domain.challenge.repository.ChallengeRepository;
import com.gaenari.backend.domain.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Override
    public boolean createChallenge() {
        if (challengeRepository.count() == 0) { // 도전과제가 없을 경우에만 생성
            int i = 1;
            for (ChallengeCategory category : ChallengeCategory.values()) {
                for (ChallengeAllTypes type : category.getTypes()) {

                    Challenge challenge = Challenge.builder()
                            .id(i++)
                            .category(category)
                            .type(type.getType())
                            .value(type.getValue()) // enum 에서 가져온 value 로 설정
                            .coin(type.getCoin())
                            .heart(type.getHeart())
                            .build();
                    challengeRepository.save(challenge);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ChallengeDto> getAllChallenges() {
        // 도전과제 목록을 조회
        List<Challenge> challenges = challengeRepository.findAll();

        // Entity -> Dto
        return challenges.stream()
                .map(challenge -> ChallengeDto.builder()
                        .id(challenge.getId())
                        .category(challenge.getCategory())
                        .type(challenge.getType())
                        .value(challenge.getValue())
                        .coin(challenge.getCoin())
                        .heart(challenge.getHeart())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteAllChallenges() {
        try {
            challengeRepository.deleteAll();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
