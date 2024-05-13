package com.gaenari.backend.domain.memberChallenge.service.impl;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import com.gaenari.backend.domain.challenge.entity.Challenge;
import com.gaenari.backend.domain.challenge.repository.ChallengeRepository;
import com.gaenari.backend.domain.client.record.RecordServiceClient;
import com.gaenari.backend.domain.client.record.dto.TotalStatisticDto;
import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberMissionDto;
import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberTrophyDto;
import com.gaenari.backend.domain.memberChallenge.entity.MemberChallenge;
import com.gaenari.backend.domain.memberChallenge.repository.MemberChallengeRepository;
import com.gaenari.backend.domain.memberChallenge.service.MemberChallengeService;
import com.gaenari.backend.global.exception.feign.ConnectFeignFailException;
import com.gaenari.backend.global.format.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberChallengeServiceImpl implements MemberChallengeService {

    private final RecordServiceClient recordServiceClient;
    private final ChallengeRepository challengeRepository;
    private final MemberChallengeRepository memberChallengeRepository;

    // 회원 모든 업적 조회
    @Override
    public List<MemberTrophyDto> getAllMemberTrophies(String memberId) {
        List<Challenge> allChallenges = challengeRepository.findByCategory(ChallengeCategory.TROPHY); // 모든 업적을 불러옴
        List<MemberChallenge> memberChallenges = memberChallengeRepository.findByMemberId(memberId); // 해당 회원의 업적 정보를 불러옴

        Map<Integer, MemberChallenge> achievedMap = memberChallenges.stream()
                .collect(Collectors.toMap(mc -> mc.getChallenge().getId(), mc -> mc));

        TotalStatisticDto statisticDto = fetchAllStatistics(memberId); // 누적 기록 조회

        return allChallenges.stream().map(challenge -> {
            MemberChallenge memberChallenge = achievedMap.get(challenge.getId());
            boolean isAchieved = memberChallenge != null && memberChallenge.getIsAchieved(); // 업적 달성 여부는 isAchieved로 판단
            double memberValue = 0.0;

            // 통계 데이터에서 적절한 값을 계산
            if (statisticDto != null) {
                if (challenge.getType() == ChallengeType.D) {
                    memberValue = Math.min(statisticDto.getDist(), challenge.getValue());
                } else if (challenge.getType() == ChallengeType.T) {
                    memberValue = Math.min(statisticDto.getTime(), challenge.getValue());
                }
            }

            return MemberTrophyDto.builder()
                    .challengeId(challenge.getId())
                    .type(challenge.getType())
                    .challengeValue(challenge.getValue())
                    .coin(isAchieved ? challenge.getCoin() * memberChallenge.getObtainable() : challenge.getCoin())
                    .isAchieved(isAchieved)
                    .memberValue(memberValue)
                    .obtainable(isAchieved ? memberChallenge.getObtainable() : 0)
                    .build();
        }).toList();
    }

    // 회원 모든 미션 조회
    @Override
    public List<MemberMissionDto> getAllMemberMissions(String memberId) {
        List<Challenge> allMissions = challengeRepository.findByCategory(ChallengeCategory.MISSION); // 모든 미션을 불러옴
        List<MemberChallenge> memberChallenges = memberChallengeRepository.findByMemberId(memberId); // 해당 회원의 미션 정보를 불러옴

        Map<Integer, MemberChallenge> achievedMap = memberChallenges.stream()
                .collect(Collectors.toMap(mc -> mc.getChallenge().getId(), mc -> mc));

        return allMissions.stream().map(mission -> {
            MemberChallenge memberChallenge = achievedMap.get(mission.getId());
            boolean isAchieved = memberChallenge != null && memberChallenge.getCount() > 0; // 달성 여부는 count로 판단
            int obtainable = isAchieved ? memberChallenge.getObtainable() : 0;

            return MemberMissionDto.builder()
                    .challengeId(mission.getId())
                    .type(mission.getType())
                    .challengeValue(mission.getValue())
                    .coin(obtainable != 0 ? mission.getCoin() * obtainable : mission.getCoin()) // 받을 수 있는 코인이 없는 경우 기본 코인 반환
                    .heart(obtainable != 0 ? mission.getHeart() * obtainable : mission.getHeart()) // 받을 수 있는 애정도가 없는 경우 기본 애정도 반환
                    .count(isAchieved ? memberChallenge.getCount() : 0)
                    .obtainable(isAchieved ? memberChallenge.getObtainable() : 0)
                    .build();
        }).toList();
    }

    // 회원 달성 업적 조회
    @Override
    public List<MemberTrophyDto> getMemberTrophies(String memberId) {
        List<MemberChallenge> memberChallenges = memberChallengeRepository.findByMemberId(memberId);
        return memberChallenges.stream()
                .map(this::mapToMemberTrophyDto)
                .toList();
    }

    // 회원 달성 미션 조회
    @Override
    public List<MemberMissionDto> getMemberMissions(String memberId) {
        List<MemberChallenge> memberChallenges = memberChallengeRepository.findByMemberId(memberId);
        return memberChallenges.stream()
                .map(this::mapToMemberMissionDto)
                .toList();
    }

    // 회원 도전과제 업데이트
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
            memberChallenge.addCount(1); // 미션 달성 횟수 1 증가
        }
        memberChallenge.updateObtainable(memberChallenge.getObtainable() + 1); // 획득 가능한 보상 개수 1 증가
    }

    // 회원 미션 달성 횟수 초기화
    @Override
    public void resetMemberMissionAchievement(String memberId, Integer challengeId) {
        MemberChallenge memberChallenge = memberChallengeRepository.findByMemberIdAndChallengeId(memberId, 1);
        memberChallenge.updateObtainable(0);
    }

    private MemberTrophyDto mapToMemberTrophyDto(MemberChallenge memberChallenge) {
        Challenge challenge = memberChallenge.getChallenge();

        // 마이크로 서비스 간 통신을 통해 누적 기록 가져오기
        TotalStatisticDto statisticDto = fetchAllStatistics(memberChallenge.getMemberId());

        double memberValue = 0.0;

        // 회원 달성 수치는 목표 수치를 넘지 않음
        if (memberChallenge.getIsAchieved() || memberValue > challenge.getValue()) memberValue = challenge.getValue();

        if (challenge.getType() == ChallengeType.D && statisticDto != null) {
            memberValue = statisticDto.getDist();
        } else if (challenge.getType() == ChallengeType.T && statisticDto != null) {
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

    // 마이크로 서비스 간 통신을 통해 누적 기록 가져오기
    private TotalStatisticDto fetchAllStatistics(String memberId) {
        ResponseEntity<GenericResponse<TotalStatisticDto>> response = recordServiceClient.getAllStatistics(memberId);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ConnectFeignFailException();
        }
        return response.getBody().getData();
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
