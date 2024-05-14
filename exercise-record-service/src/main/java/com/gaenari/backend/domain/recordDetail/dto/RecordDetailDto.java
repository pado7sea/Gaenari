package com.gaenari.backend.domain.recordDetail.dto;

import com.gaenari.backend.domain.record.dto.enumType.ChallengeType;
import com.gaenari.backend.domain.record.dto.enumType.ExerciseType;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordDetailDto {
    @Schema(description = "운동 ID", example = "1")
    private Long exerciseId;

    @Schema(description = "날짜 및 시간", example = "2024-05-13T10:15:30")
    private LocalDateTime date;

    @Schema(description = "운동 종류", example = "P")
    private ExerciseType exerciseType;

    @Schema(description = "프로그램 타입", example = "D")
    private ProgramType programType;

    @Schema(description = "프로그램 정보")
    private ProgramInfoDto program;

    @Schema(description = "운동 기록 정보", implementation = DetailRecordDto.class)
    private DetailRecordDto record;

    @Schema(description = "페이스 정보")
    private PaceDto paces;

    @Schema(description = "심박수 정보")
    private HeartrateDto heartrates;

    private List<TrophyDto> trophies;
    private List<MissionDto> missions;

    @Schema(description = "획득 가능 코인", example = "100")
    private int attainableCoin;

    @Schema(description = "획득 가능 하트", example = "50")
    private int attainableHeart;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailRecordDto {
        @Schema(description = "거리", example = "5.0")
        private Double distance;

        @Schema(description = "시간", example = "30.0")
        private Double time;

        @Schema(description = "소모 칼로리", example = "150.0")
        private Double cal;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaceDto {
        @Schema(description = "평균 페이스", example = "5.0")
        private Double average;

        @Schema(description = "분당 페이스 리스트", type = "array", example = "[5, 6, 7]")
        private List<Integer> arr;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HeartrateDto {
        @Schema(description = "평균 심박수", example = "120.0")
        private Double average;

        @Schema(description = "최대 심박수", example = "150")
        private Integer max;

        @Schema(description = "최소 심박수", example = "90")
        private Integer min;

        @Schema(description = "분당 심박수 리스트", type = "array", example = "[120, 125, 130]")
        private List<Integer> arr;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrophyDto {
        @Schema(description = "트로피 ID", example = "1")
        private Integer id;

        @Schema(description = "도전 종류", example = "D")
        private ChallengeType type;

        @Schema(description = "획득 코인", example = "50")
        private Integer coin;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionDto {
        @Schema(description = "미션 ID", example = "1")
        private Integer id;

        @Schema(description = "도전 종류", example = "T")
        private ChallengeType type;

        @Schema(description = "목표 값", example = "30")
        private Integer value;

        @Schema(description = "획득 코인", example = "100")
        private Integer coin;

        @Schema(description = "획득 하트", example = "20")
        private Integer heart;
    }
}

