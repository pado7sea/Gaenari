package com.gaenari.backend.domain.challengeFeign.dto;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrophyDto {
    @Schema(description = "도전 과제 ID", example = "1")
    private Integer id;

    @Schema(description = "도전 과제 유형", allowableValues = {"D", "T"})
    private ChallengeType type;

    @Schema(description = "획득 코인", example = "50")
    private Integer coin;
}