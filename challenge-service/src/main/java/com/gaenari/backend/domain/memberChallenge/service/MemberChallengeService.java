package com.gaenari.backend.domain.memberChallenge.service;

import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberMissionDto;
import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberTrophyDto;

import java.util.List;

public interface MemberChallengeService {

    /**
     * 지정된 회원의 모든 업적을 조회한다.
     *
     * @param accountId String 형태로, 조회할 회원의 고유 식별자.
     * @return List<MemberTrophyDto> 회원이 달성한 모든 업적에 대한 정보 목록을 반환한다.
     */
    List<MemberTrophyDto> getAllMemberTrophies(String accountId);

    /**
     * 지정된 회원의 모든 미션을 조회한다.
     *
     * @param accountId String 형태로, 조회할 회원의 고유 식별자.
     * @return List<MemberMissionDto> 회원이 달성한 모든 미션에 대한 정보 목록을 반환한다.
     */
    List<MemberMissionDto> getAllMemberMissions(String accountId);

    /**
     * 지정된 회원이 달성한 업적만을 조회한다.
     *
     * @param accountId String 형태로, 조회할 회원의 고유 식별자.
     * @return List<MemberTrophyDto> 회원이 달성한 업적에 대한 정보 목록을 반환한다.
     */
    List<MemberTrophyDto> getMemberTrophies(String accountId);

    /**
     * 지정된 회원이 달성한 미션만을 조회한다.
     *
     * @param accountId String 형태로, 조회할 회원의 고유 식별자.
     * @return List<MemberMissionDto> 회원이 달성한 미션에 대한 정보 목록을 반환한다.
     */
    List<MemberMissionDto> getMemberMissions(String accountId);

    /**
     * 지정된 회원의 특정 도전과제를 업데이트한다.
     *
     * @param accountId String 형태로, 업데이트할 회원의 고유 식별자.
     * @param challengeId Integer 형태로, 업데이트할 도전과제의 고유 식별자.
     */
    void updateMemberChallenge(String accountId, Integer challengeId);

    /**
     * 지정된 회원의 특정 미션의 달성 횟수를 초기화한다.
     *
     * @param accountId String 형태로, 초기화할 회원의 고유 식별자.
     * @param challengeId Integer 형태로, 초기화할 미션의 고유 식별자.
     */
    void resetMemberMissionAchievement(String accountId, Integer challengeId);

}
