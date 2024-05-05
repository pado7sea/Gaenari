package com.gaenari.backend.domain.challengeFeign.service.impl;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import com.gaenari.backend.domain.challengeFeign.service.AchievedChallengeFeignService;
import com.gaenari.backend.domain.challengeFeign.dto.MissionDto;
import com.gaenari.backend.domain.challengeFeign.dto.RecordAboutChallengeDto;
import com.gaenari.backend.domain.challengeFeign.dto.TrophyDto;
import com.gaenari.backend.domain.challenge.dto.responseDto.ChallengeDto;
import com.gaenari.backend.domain.challenge.entity.Challenge;
import com.gaenari.backend.domain.challenge.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievedChallengeFeignServiceImpl implements AchievedChallengeFeignService {

    private final ChallengeRepository challengeRepository;

    @Override
    public List<Integer> getAchievedTrophyIds(RecordAboutChallengeDto recordDto) {
        return getAchievedIds(recordDto.getStatisticDistance(), recordDto.getStatisticTime(), ChallengeCategory.TROPHY);
    }

    @Override
    public List<Integer> getAchievedMissionIds(RecordAboutChallengeDto recordDto) {
        return getAchievedIds(recordDto.getDistance(), recordDto.getTime(), ChallengeCategory.MISSION);
    }

    private List<Integer> getAchievedIds(Double distance, Double time, ChallengeCategory category) {
        List<Integer> achievedIds = new ArrayList<>();
        List<ChallengeDto> challenges = getChallengesByCategory(category);

        for (ChallengeDto challenge : challenges) {
            boolean achieved = false;
            if (challenge.getType() == ChallengeType.T) {
                achieved = time >= challenge.getValue();
            } else if (challenge.getType() == ChallengeType.D) {
                achieved = distance >= challenge.getValue();
            }
            if (achieved) {
                achievedIds.add(challenge.getId());
                System.out.println(challenge.getType());
            }
        }

        return achievedIds;
    }

    private List<ChallengeDto> getChallengesByCategory(ChallengeCategory category) {
        return challengeRepository.findByCategory(category).stream()
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
    public TrophyDto getTrophy(Integer challengeId) {
        Optional<Challenge> optionalChallenge = challengeRepository.findById(Long.valueOf(challengeId));
        if (optionalChallenge.isPresent()) {
            Challenge challenge = optionalChallenge.get();
            return TrophyDto.builder()
                    .id(challenge.getId())
                    .type(challenge.getType())
                    .coin(challenge.getCoin())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public MissionDto getMission(Integer challengeId) {
        Optional<Challenge> optionalChallenge = challengeRepository.findById(Long.valueOf(challengeId));
        if (optionalChallenge.isPresent()) {
            Challenge challenge = optionalChallenge.get();
            return MissionDto.builder()
                    .id(challenge.getId())
                    .type(challenge.getType())
                    .value(challenge.getValue())
                    .coin(challenge.getCoin())
                    .heart(challenge.getHeart())
                    .build();
        } else {
            return null;
        }
    }
}

