package com.gaenari.backend.domain.memberChallenge.dto.responseDto;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberTrophyDto {
    private Integer challengeId;
    private ChallengeType type;     // enum: D(거리), T(시간)
    private Integer challengeValue; // 목표 수치
    private Integer coin;           // 코인
    private Boolean isAchieved;     // 업적 달성 여부
    private Double memberValue;    // 업적 회원 달성 수치(누적기록). 최대값:목표수치
    private Integer obtainable;     // 획득하지 않은 보상 개수
}
