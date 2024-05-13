package com.gaenari.backend.domain.challengeFeign.dto;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionDto {
    @Schema(description = "도전 과제 ID", example = "1")
    private Integer id;

    @Schema(description = "도전 과제 유형", allowableValues = {"D", "T"})
    private ChallengeType type;

    @Schema(description = "도전 과제 값", example = "1000")
    private Integer value;

    @Schema(description = "획득 코인", example = "50")
    private Integer coin;

    @Schema(description = "획득 하트", example = "30")
    private Integer heart;
}
