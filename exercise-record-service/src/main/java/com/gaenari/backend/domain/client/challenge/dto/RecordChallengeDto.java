package com.gaenari.backend.domain.client.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordChallengeDto {
    @Schema(description = "도전 고유 ID", example = "1")
    private Integer challengeId;

    @Schema(description = "도전 달성 여부", example = "false")
    private Boolean isObtained = false;
}
