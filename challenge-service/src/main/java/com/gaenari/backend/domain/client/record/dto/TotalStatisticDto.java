package com.gaenari.backend.domain.client.record.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalStatisticDto {
    @Schema(description = "운동 총 시간")
    private Double time;

    @Schema(description = "운동 총 거리")
    private Double dist;

    @Schema(description = "운동 총 소모 칼로리")
    private Double cal;

    @Schema(description = "운동 평균 페이스")
    private Double pace;

    @Schema(description = "최근 운동 날짜")
    private LocalDateTime date;

    @Schema(description = "운동 기록 수")
    private Integer count;
}
