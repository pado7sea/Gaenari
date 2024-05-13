package com.gaenari.backend.domain.challengeFeign.service;

import com.gaenari.backend.domain.challenge.dto.responseDto.ChallengeDto;
import com.gaenari.backend.domain.challengeFeign.dto.RecordAboutChallengeDto;

import java.util.List;

public interface AchievedChallengeFeignService {

    /**
     * 주어진 레코드에 대해 새로 달성한 도전과제의 ID 목록을 반환한다.
     *
     * @param recordDto RecordAboutChallengeDto 객체로, 사용자의 운동 기록과 관련된 정보를 포함한다.
     * @return List<Integer> 새로 달성한 도전과제의 ID 목록을 반환한다.
     */
    List<Integer> getNewlyAchievedChallengeIds(RecordAboutChallengeDto recordDto);

    /**
     * 주어진 도전과제 ID 목록에 해당하는 도전과제 정보를 반환한다.
     *
     * @param challengeIds List<Integer>로, 조회할 도전과제의 ID 목록을 포함한다.
     * @return List<ChallengeDto> 주어진 ID 목록에 해당하는 도전과제 정보를 포함하는 ChallengeDto 객체 목록을 반환한다.
     */
    List<ChallengeDto> getChallenges(List<Integer> challengeIds);

}
