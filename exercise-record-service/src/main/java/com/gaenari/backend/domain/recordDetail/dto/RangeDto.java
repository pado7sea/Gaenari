package com.gaenari.backend.domain.recordDetail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RangeDto {
    @Schema(description = "구간 ID", example = "1")
    private Long id;

    @Schema(description = "달리기 여부", example = "true")
    private Boolean isRunning;

    @Schema(description = "시간", example = "30.0")
    private Double time;

    @Schema(description = "속도", example = "10.0")
    private Double speed;
}