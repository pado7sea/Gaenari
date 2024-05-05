package com.gaenari.backend.domain.challenge.service.impl;

import com.gaenari.backend.domain.challenge.dto.responseDto.AchievedMissionDto;
import com.gaenari.backend.domain.challenge.dto.responseDto.AchievedTrophyDto;
import com.gaenari.backend.domain.challenge.repository.ChallengeRepository;
import com.gaenari.backend.domain.challenge.service.AchievedChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievedChallengeServiceImpl implements AchievedChallengeService {

    private final ChallengeRepository challengeRepository;

    @Override
    public List<AchievedTrophyDto> getAchievedTrophies(Long memberId) {

        // 아직 달성 안한 것 중에서
        // 누적 기록 조회해서
        // 달성했는지 파악
        // values에 값넣음
        // 이미 달성 한 건 value를 -1로 넣? 그럴까? ㅇㅋㅇㅋ
        // 달성하고 보상 안받은거 체크



        return List.of();
    }

    @Override
    public List<AchievedMissionDto> getAchievedMissions(Long memberId) {

        // 미션 순회 하면서
        // 그냥 싹 다 반환 다 해..

        return List.of();
    }

}
