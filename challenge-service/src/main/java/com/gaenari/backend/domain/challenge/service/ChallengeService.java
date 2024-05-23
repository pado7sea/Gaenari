package com.gaenari.backend.domain.challenge.service;

import com.gaenari.backend.domain.challenge.dto.responseDto.ChallengeDto;

import java.util.List;

public interface ChallengeService {

    /**
     * 새로운 도전과제를 생성한다.
     *
     * @return boolean 도전과제 생성이 성공적으로 완료되었다면 true, 실패했다면 false를 반환한다.
     */
    boolean createChallenge();

    /**
     * 시스템에 등록된 모든 도전과제를 조회한다.
     *
     * @return List<ChallengeDto> 시스템에 등록된 모든 도전과제의 목록을 반환한다.
     */
    List<ChallengeDto> getAllChallenges();

    /**
     * 시스템에 등록된 모든 도전과제를 삭제한다.
     *
     * @return boolean 모든 도전과제 삭제가 성공적으로 완료되었다면 true, 실패했다면 false를 반환한다.
     */
    boolean deleteAllChallenges();
}