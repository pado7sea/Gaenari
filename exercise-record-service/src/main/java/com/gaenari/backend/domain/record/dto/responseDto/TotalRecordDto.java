package com.gaenari.backend.domain.record.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalRecordDto {
    @Schema(description = "연", example = "2024")
    private int year;

    @Schema(description = "월", example = "5")
    private int month;

    @Schema(description = "운동 기록 리스트", type = "array")
    private List<ExerciseRecordDto> exerciseRecords;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExerciseRecordDto {
        @Schema(description = "일")
        private String day;

        @Schema(description = "하루에 한 운동 기록들")
        private List<DailyRecordDto> records;
    }
}

