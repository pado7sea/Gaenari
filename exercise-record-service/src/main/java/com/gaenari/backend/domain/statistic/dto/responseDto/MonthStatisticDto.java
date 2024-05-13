package com.gaenari.backend.domain.statistic.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthStatisticDto {
    @Schema(description = "연", example = "2024")
    private int year;

    @Schema(description = "월", example = "5")
    private int month;

    @Schema(description = "운동 총 시간", example = "150.0", type = "double")
    private Double time;

    @Schema(description = "운동 총 거리", example = "10.0", type = "double")
    private Double dist;

    @Schema(description = "운동 총 소모 칼로리", example = "500.0", type = "double")
    private Double cal;

    @Schema(description = "운동 평균 페이스", example = "5.0", type = "double")
    private Double pace;

    @Schema(description = "운동 평균 심박수", example = "120.0", type = "double")
    private Double heart;
}
