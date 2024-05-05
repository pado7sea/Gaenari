package com.gaenari.backend.domain.challenge.service;

import com.gaenari.backend.domain.challenge.dto.responseDto.AchievedMissionDto;
import com.gaenari.backend.domain.challenge.dto.responseDto.AchievedTrophyDto;

import java.util.List;

public interface AchievedChallengeService {

    List<AchievedTrophyDto> getAchievedTrophies(Long memberId);

    List<AchievedMissionDto> getAchievedMissions(Long memberId);

}
