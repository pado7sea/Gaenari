package com.gaenari.backend.domain.memberChallenge.service.impl;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import com.gaenari.backend.domain.challenge.entity.Challenge;
import com.gaenari.backend.domain.challenge.repository.ChallengeRepository;
import com.gaenari.backend.domain.client.RecordServiceClient;
import com.gaenari.backend.domain.client.dto.TotalStatisticDto;
import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberMissionDto;
import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberTrophyDto;
import com.gaenari.backend.domain.memberChallenge.entity.MemberChallenge;
import com.gaenari.backend.domain.memberChallenge.repository.MemberChallengeRepository;
import com.gaenari.backend.domain.memberChallenge.service.MemberChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberChallengeServiceImpl implements MemberChallengeService {

    private final RecordServiceClient recordServiceClient;
    private final ChallengeRepository challengeRepository;
    private final MemberChallengeRepository memberChallengeRepository;

    @Override
    public List<MemberTrophyDto> getMemberTrophies(String memberId) {
        List<MemberChallenge> memberChallenges = memberChallengeRepository.findByMemberId(memberId);
        return memberChallenges.stream()
                .map(this::mapToMemberTrophyDto)
                .toList();
    }

    @Override
    public List<MemberMissionDto> getMemberMissions(String memberId) {
        List<MemberChallenge> memberChallenges = memberChallengeRepository.findByMemberId(memberId);
        return memberChallenges.stream()
                .map(this::mapToMemberMissionDto)
                .toList();
    }

    @Override
    public void updateMemberChallenge(String memberId, Integer challengeId) {
        MemberChallenge memberChallenge = memberChallengeRepository.findByMemberIdAndChallengeId(memberId, challengeId);
        Challenge challenge = challengeRepository.findById(challengeId);

        // 회원의 도전과제 정보가 있는 경우에만 업데이트를 수행
        if (memberChallenge == null) {
            // 회원의 도전과제 정보가 없는 경우 새로운 도전과제 정보 생성
            memberChallenge = MemberChallenge.builder()
                    .memberId(memberId)
                    .challenge(challenge)
                    .isAchieved(false)
                    .count(0)
                    .obtainable(0)
                    .build();
            memberChallengeRepository.save(memberChallenge);
        }
        
        ChallengeCategory challengeCategory = challenge.getCategory();
        if (challengeCategory == ChallengeCategory.TROPHY) {
            memberChallenge.updateIsAchieved(true); // 업적 달성 여부 true

        } else if (challengeCategory == ChallengeCategory.MISSION) {
            memberChallenge.updateCount(1); // 미션 달성 횟수 1 증가
        }
        memberChallenge.updateObtainable(memberChallenge.getObtainable() + 1); // 획득 가능한 보상 개수 1 증가
    }

    @Override
    public void resetMemberMissionAchievement(String memberId, Integer challengeId) {
        MemberChallenge memberChallenge = memberChallengeRepository.findByMemberIdAndChallengeId(memberId, 1);
        memberChallenge.updateObtainable(0);
    }

    private MemberTrophyDto mapToMemberTrophyDto(MemberChallenge memberChallenge) {

        Challenge challenge = memberChallenge.getChallenge();

        // 마이크로 서비스 간 통신을 통해 누적 기록 가져오기
        TotalStatisticDto statisticDto = recordServiceClient.getAllStatistics(memberChallenge.getMemberId());

        double memberValue = 0.0;

        // 회원 달성 수치는 목표 수치를 넘지 않음
        if (memberChallenge.getIsAchieved() || memberValue > challenge.getValue()) memberValue = challenge.getValue();

        if (challenge.getType() == ChallengeType.D) {
            memberValue = statisticDto.getDist();
        } else if (challenge.getType() == ChallengeType.T) {
            memberValue = statisticDto.getTime();
        }

        return MemberTrophyDto.builder()
                .challengeId(challenge.getId())
                .type(challenge.getType())
                .challengeValue(challenge.getValue())
                .coin(challenge.getCoin() * memberChallenge.getObtainable()) // 도전과제 코인 금액 * 획득하지 않은 보상 개수
                .isAchieved(memberChallenge.getIsAchieved())
                .memberValue(memberValue)
                .obtainable(memberChallenge.getObtainable())
                .build();
    }

    private MemberMissionDto mapToMemberMissionDto(MemberChallenge memberChallenge) {

        Challenge challenge = memberChallenge.getChallenge();

        return MemberMissionDto.builder()
                .challengeId(challenge.getId())
                .type(challenge.getType())
                .challengeValue(challenge.getValue())
                .coin(challenge.getCoin() * memberChallenge.getObtainable())
                .heart(challenge.getHeart() * memberChallenge.getObtainable())
                .count(memberChallenge.getCount())
                .obtainable(memberChallenge.getObtainable())
                .build();
    }

}
