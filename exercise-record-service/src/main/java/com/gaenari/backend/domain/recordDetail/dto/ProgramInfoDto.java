package com.gaenari.backend.domain.recordDetail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramInfoDto {
    @Schema(description = "프로그램 ID", example = "1")
    private Long programId;

    @Schema(description = "프로그램 타이틀", example = "str")
    private String programTitle;

    @Schema(description = "목표 값", example = "10.0")
    private Double targetValue;

    @Schema(description = "인터벌 정보")
    private IntervalDto intervalInfo;
}
