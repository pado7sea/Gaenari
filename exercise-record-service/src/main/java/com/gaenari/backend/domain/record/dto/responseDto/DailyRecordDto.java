package com.gaenari.backend.domain.record.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyRecordDto {
    @Schema(description = "운동 기록 ID")
    private Long recordId;

    @Schema(description = "운동 일자", example = "2024-05-13T04:36:56.379Z")
    private LocalDateTime recordDate;

    @Schema(description = "운동 시간", example = "300.0")
    private Double recordTime;

    @Schema(description = "운동 거리", example = "1.0")
    private Double recordDist;

    @Schema(description = "운동 페이스", example = "3600.0")
    private Double recordPace;
}
