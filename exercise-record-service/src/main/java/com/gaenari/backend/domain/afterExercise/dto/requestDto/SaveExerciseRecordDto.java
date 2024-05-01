package com.gaenari.backend.domain.afterExercise.dto.requestDto;

import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveExerciseRecordDto {
    private LocalDateTime date;
    private String exerciseType;
    private String programType;
    private ProgramDto program;
    private RecordDto record;
    private SpeedDto speeds;
    private HeartrateDto heartrates;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProgramDto {
        private Long programId;
        private Double targetValue;
        private IntervalDto intervalInfo;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntervalDto {
        private Double duration;
        private Integer setCount;
        private Integer rangeCount;
        private List<RangeDto> ranges;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RangeDto {
        private Integer rangeId;
        private Boolean isRunning;
        private Double time;
        private Double speed;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecordDto {
        private Double distance;
        private Double time;
        private Double cal;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpeedDto {
        private Double average; // 평균 속도 (페이스 아님)
        private List<Integer> arr; // 분당 속도 (페이스 아님)
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HeartrateDto {
        private Double average;
        private Integer max;
        private Integer min;
        private List<Integer> arr; // 분당 심박수
    }


}
