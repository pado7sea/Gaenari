package com.gaenari.backend.domain.challenge.service;

import com.gaenari.backend.domain.challenge.dto.responseDto.ChallengeDto;

import java.util.List;

public interface ChallengeService {

    boolean createChallenge();

    List<ChallengeDto> getAllChallenges();

    boolean deleteAllChallenges();
}
