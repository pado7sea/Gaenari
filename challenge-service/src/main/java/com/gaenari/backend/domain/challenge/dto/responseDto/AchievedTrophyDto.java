package com.gaenari.backend.domain.challenge.dto.responseDto;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeAllTypes;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchievedTrophyDto {
    private Integer challengeId;
    private Integer challengeValue; // 목표 수치
    private ChallengeAllTypes type;     // enum: D(거리), T(시간)
    private Integer coin;           // 코인
    private Integer memberValue;    // 도전 달성 수치
    private Boolean isObtained;          // 보상 획득 여부
}
