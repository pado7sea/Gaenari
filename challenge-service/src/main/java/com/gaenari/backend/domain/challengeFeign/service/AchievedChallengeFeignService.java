package com.gaenari.backend.domain.challengeFeign.service;

import com.gaenari.backend.domain.challenge.dto.responseDto.ChallengeDto;
import com.gaenari.backend.domain.challengeFeign.dto.RecordAboutChallengeDto;

import java.util.List;

public interface AchievedChallengeFeignService {

    List<Integer> getNewlyAchievedChallengeIds(RecordAboutChallengeDto recordDto);

    List<ChallengeDto> getChallenges(List<Integer> challengeIds);

}
