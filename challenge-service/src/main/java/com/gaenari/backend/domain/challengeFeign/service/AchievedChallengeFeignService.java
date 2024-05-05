package com.gaenari.backend.domain.challengeFeign.service;

import com.gaenari.backend.domain.challengeFeign.dto.MissionDto;
import com.gaenari.backend.domain.challengeFeign.dto.RecordAboutChallengeDto;
import com.gaenari.backend.domain.challengeFeign.dto.TrophyDto;

import java.util.List;

public interface AchievedChallengeFeignService {

    List<Integer> getAchievedTrophyIds(RecordAboutChallengeDto recordDto);

    List<Integer> getAchievedMissionIds(RecordAboutChallengeDto recordDto);

    TrophyDto getTrophy(Integer challengeId);

    MissionDto getMission(Integer challengeId);
}
