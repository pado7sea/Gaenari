package com.gaenari.backend.domain.statistic.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDto {
    @Schema(description = "일자", example = "2024-05-05")
    private LocalDate date;

    @Schema(description = "일일 통계", implementation = DailyStatisticDto.class)
    private DailyStatisticDto dailyStatistic;

}
