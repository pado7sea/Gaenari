package com.gaenari.backend.domain.memberChallenge.service;

import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberMissionDto;
import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberTrophyDto;

import java.util.List;

public interface MemberChallengeService {

    List<MemberTrophyDto> getMemberTrophies(Long memberId);

    List<MemberMissionDto> getMemberMissions(Long memberId);

    // 회원 도전과제 업데이트
    void updateMemberChallenge(Long memberId, Integer challengeId);

    // 회원 미션 달성 횟수 초기화
    void resetMemberMissionAchievement(Long memberId, Integer challengeId);

}
