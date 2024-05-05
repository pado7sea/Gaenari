package com.gaenari.backend.domain.afterExercise.dto.requestDto;

import com.gaenari.backend.domain.record.dto.enumType.ExerciseType;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import com.gaenari.backend.domain.recordDetail.dto.ProgramInfoDto;
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
    private ExerciseType exerciseType;

    private ProgramType programType;
    private ProgramInfoDto program;

    private RecordDto record;
    private SpeedDto speeds;
    private HeartrateDto heartrates;

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
