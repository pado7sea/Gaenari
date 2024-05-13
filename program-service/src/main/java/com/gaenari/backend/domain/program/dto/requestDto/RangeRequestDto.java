package com.gaenari.backend.domain.program.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RangeRequestDto {
    @Schema(description = "달리기 여부", example = "true")
    private Boolean isRunning;

    @Schema(description = "시간 (초)", example = "60")
    private Double time;

    @Schema(description = "속도 (km/h)", example = "10.0")
    private Double speed;
}