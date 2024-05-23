package com.gaenari.backend.domain.record.dto.responseDto;

import com.gaenari.backend.domain.record.dto.enumType.ExerciseType;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Schema(description = "운동 타입", example = "P")
    private ExerciseType exerciseType;

    @Schema(description = "프로그램 타입", example = "D")
    private ProgramType programType;

    @Schema(description = "운동 시간", example = "300.0")
    private Double recordTime;

    @Schema(description = "운동 거리", example = "1.0")
    private Double recordDist;

    @Schema(description = "운동 페이스", example = "3600.0")
    private Double recordPace;

    @Schema(description = "운동 총 소모 칼로리", example = "400.0")
    private Double recordCal;

}
