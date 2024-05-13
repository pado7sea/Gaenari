package com.gaenari.backend.domain.challengeFeign.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordAboutChallengeDto {
    @Schema(description = "회원 ID")
    private String memberId;

    @Schema(description = "운동 기록 ID")
    private Long recordId;

    @Schema(description = "해당 기록 거리", example = "10.5")
    private Double distance;

    @Schema(description = "해당 기록 시간", example = "3600.0")
    private Double time;

    @Schema(description = "멤버 누적 거리", example = "150.0")
    private Double statisticDistance;

    @Schema(description = "멤버 누적 시간", example = "7200.0")
    private Double statisticTime;
}
