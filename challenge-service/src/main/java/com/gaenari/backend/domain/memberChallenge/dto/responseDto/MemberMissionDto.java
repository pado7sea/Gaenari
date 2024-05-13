package com.gaenari.backend.domain.memberChallenge.dto.responseDto;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberMissionDto {
    @Schema(description = "도전 과제 ID", example = "1")
    private Integer challengeId;

    @Schema(description = "도전 과제 유형", allowableValues = {"D", "T"})
    private ChallengeType type;

    @Schema(description = "도전 과제 목표 수치")
    private Integer challengeValue;

    @Schema(description = "획득 코인")
    private Integer coin;

    @Schema(description = "획득 하트")
    private Integer heart;

    @Schema(description = "미션 달성 횟수")
    private Integer count;

    @Schema(description = "획득하지 않은 보상 개수")
    private Integer obtainable;
}