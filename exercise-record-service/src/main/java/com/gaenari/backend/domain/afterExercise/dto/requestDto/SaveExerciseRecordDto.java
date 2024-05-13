package com.gaenari.backend.domain.afterExercise.dto.requestDto;

import com.gaenari.backend.domain.record.dto.enumType.ExerciseType;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveExerciseRecordDto {
    @Schema(description = "운동 일시", example = "2024-05-09T11:42:01.892Z")
    private LocalDateTime date;

    @Schema(description = "운동 타입", example = "P")
    private ExerciseType exerciseType;

    @Schema(description = "프로그램 타입", example = "D")
    private ProgramType programType;

    @Schema(description = "프로그램 정보")
    private ProgramInfoDto program;

    @Schema(description = "누적 거리, 시간")
    private RecordDto record;

    @Schema(description = "속도")
    private SpeedDto speeds;

    @Schema(description = "심박수")
    private HeartrateDto heartrates;

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProgramInfoDto {
        @Schema(description = "프로그램 ID", example = "3")
        private Long programId;

        @Schema(description = "인터벌 프로그램 정보")
        private IntervalDto intervalInfo;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntervalDto {
        @Schema(description = "구간 리스트")
        private List<RangeDto> ranges;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RangeDto {
        @Schema(description = "달리기 여부", example = "true")
        private Boolean isRunning;

        @Schema(description = "구간 시간 (sec)", example = "300")
        private Double time;

        @Schema(description = "구간 속도 (km/h)", example = "6")
        private Double speed;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecordDto {
        @Schema(description = "누적 거리 (km)", example = "10")
        private Double distance;

        @Schema(description = "누적 시간 (sec)", example = "300")
        private Double time;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpeedDto {
        @Schema(description = "평균 속도 (km/h)", example = "6")
        private Double average;

        @Schema(description = "분당 속도 리스트", example = "[4,6,8,10,4]")
        private List<Integer> arr;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HeartrateDto {
        @Schema(description = "평균 심박수", example = "110")
        private Double average;

        @Schema(description = "분당 심박수 리스트", example = "[130,120,110,103,111]")
        private List<Integer> arr;
    }
}
