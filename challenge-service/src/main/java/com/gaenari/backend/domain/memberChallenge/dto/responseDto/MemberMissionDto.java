package com.gaenari.backend.domain.memberChallenge.dto.responseDto;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberMissionDto {
    private Integer challengeId;
    private ChallengeType type;     // enum: D(거리), T(시간)
    private Integer challengeValue; // 목표 수치
    private Integer coin;           // 코인
    private Integer heart;          // 애정도
    private Integer count;          // 미션 달성 횟수
    private Integer obtainable;     // 획득하지 않은 보상 개수
}