package com.gaenari.backend.domain.challenge.dto.responseDto;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeAllTypes;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchievedMissionDto {
    private Integer challengeId;
    private Integer challengeValue; // 목표 수치
    private ChallengeAllTypes type;     // enum: D(거리), T(시간)
    private Integer coin;           // 코인
    private Integer heart;          // 애정도
    private Integer memberValue;    // 도전 달성 횟수
    private Boolean isObtained;          // 보상 획득 여부
    private Integer notRecieveCount;    // 받지 않은 보상 개수
}