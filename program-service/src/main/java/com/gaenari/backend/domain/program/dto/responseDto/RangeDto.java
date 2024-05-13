package com.gaenari.backend.domain.program.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RangeDto {
    @Schema(description = "구간 ID")
    private Long id;

    @Schema(description = "뛰는 시간 여부", example = "true")
    private Boolean isRunning;

    @Schema(description = "구간 시간 (단위: sec)", example = "60.0")
    private Double time;

    @Schema(description = "구간 속도 (단위: km/h)", example = "8.0")
    private Double speed;
}
