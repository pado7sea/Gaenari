package com.gaenari.backend.domain.memberChallenge.service;

import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberMissionDto;
import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberTrophyDto;

import java.util.List;

public interface MemberChallengeService {

    // 회원 업적 조회
    List<MemberTrophyDto> getMemberTrophies(String memberId);

    // 회원 미션 조회
    List<MemberMissionDto> getMemberMissions(String memberId);

    // 회원 도전과제 업데이트
    void updateMemberChallenge(String memberId, Integer challengeId);

    // 회원 미션 달성 횟수 초기화
    void resetMemberMissionAchievement(String memberId, Integer challengeId);

}
