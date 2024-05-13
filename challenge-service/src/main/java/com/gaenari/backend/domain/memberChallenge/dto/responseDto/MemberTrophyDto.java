package com.gaenari.backend.domain.memberChallenge.dto.responseDto;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberTrophyDto {
    @Schema(description = "도전 과제 ID", example = "1")
    private Integer challengeId;

    @Schema(description = "도전 과제 유형", allowableValues = {"D", "T"})
    private ChallengeType type;

    @Schema(description = "도전 과제 목표 수치")
    private Integer challengeValue;

    @Schema(description = "획득 코인")
    private Integer coin;

    @Schema(description = "도전 과제 달성 여부")
    private Boolean isAchieved;

    @Schema(description = "회원의 도전 과제 달성 수치 (누적 기록). 최대값: 목표 수치")
    private Double memberValue;

    @Schema(description = "획득하지 않은 보상 개수")
    private Integer obtainable;
}
