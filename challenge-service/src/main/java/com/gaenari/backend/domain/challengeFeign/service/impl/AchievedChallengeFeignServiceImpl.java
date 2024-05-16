package com.gaenari.backend.domain.challengeFeign.service.impl;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import com.gaenari.backend.domain.challenge.dto.responseDto.ChallengeDto;
import com.gaenari.backend.domain.challenge.entity.Challenge;
import com.gaenari.backend.domain.challenge.repository.ChallengeRepository;
import com.gaenari.backend.domain.challengeFeign.dto.RecordAboutChallengeDto;
import com.gaenari.backend.domain.challengeFeign.service.AchievedChallengeFeignService;
import com.gaenari.backend.domain.memberChallenge.entity.MemberChallenge;
import com.gaenari.backend.domain.memberChallenge.repository.MemberChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievedChallengeFeignServiceImpl implements AchievedChallengeFeignService {

    private final ChallengeRepository challengeRepository;
    private final MemberChallengeRepository memberChallengeRepository;

    @Override
    public List<Integer> getNewlyAchievedChallengeIds(RecordAboutChallengeDto recordDto) {
        // 회원이 기존에 달성한 도전과제 아이디 리스트 조회
        List<MemberChallenge> achievedChallenges = memberChallengeRepository.findByAccountIdAndIsAchievedIsTrue(recordDto.getAccountId());
        List<Integer> achievedChallengeIds = achievedChallenges.stream()
                .map(memberChallenge -> memberChallenge.getChallenge().getId())
                .toList();

        // 해당 도전과제 아이디 리스트를 제외한 도전 과제 엔티티 조회
        List<Challenge> challenges;
        if (achievedChallengeIds.isEmpty()) {
            // 만약 achievedChallengeIds가 비어 있다면, 모든 도전 과제를 조회한다.
            challenges = challengeRepository.findAll();
        } else {
            // achievedChallengeIds가 비어 있지 않다면, 해당 도전 과제 아이디를 제외한 도전 과제를 조회한다.
            challenges = challengeRepository.findByIdNotIn(achievedChallengeIds);
        }

        // 새로 달성한 도전과제 아이디 리스트
        return getIds(recordDto, challenges);
    }

    private static List<Integer> getIds(RecordAboutChallengeDto recordDto, List<Challenge> challenges) {
        List<Integer> newlyAchievedIds = new ArrayList<>();

        // 도전과제 달성 여부 판단
        for (Challenge challenge : challenges) {
            boolean achieved = false;
            if (challenge.getType() == ChallengeType.T) {
                achieved = recordDto.getTime() >= challenge.getValue();
            } else if (challenge.getType() == ChallengeType.D) {
                achieved = recordDto.getDistance() >= challenge.getValue();
            }
            if (achieved) {
                // 새로 달성한 도전과제 아이디 리스트에 추가
                newlyAchievedIds.add(challenge.getId());
            }
        }
        return newlyAchievedIds;
    }

    @Override
    public List<ChallengeDto> getChallenges(List<Integer> challengeIds) {
        return challengeIds.stream()
                .map(challengeId -> {
                    Challenge challenge = challengeRepository.findById(challengeId);
                    if (challenge.getCategory() == ChallengeCategory.MISSION) {

                        return ChallengeDto.builder()
                                .id(challenge.getId())
                                .category(challenge.getCategory())
                                .type(challenge.getType())
                                .value(challenge.getValue())
                                .coin(challenge.getCoin())
                                .heart(challenge.getHeart())
                                .build();

                    } else if (challenge.getCategory() == ChallengeCategory.TROPHY) {

                        return ChallengeDto.builder()
                                .id(challenge.getId())
                                .category(challenge.getCategory())
                                .type(challenge.getType())
                                .value(challenge.getValue())
                                .coin(challenge.getCoin())
                                .heart(0)
                                .build();
                    }

                    return null;
                }).toList();
    }
}