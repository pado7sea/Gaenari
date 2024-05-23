package com.gaenari.backend.domain.program.dto.responseDto;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDetailDto {
    @Schema(description = "운동 프로그램 ID", example = "1")
    private Long programId;

    @Schema(description = "운동 프로그램 제목", example = "매일 걷기")
    private String programTitle;

    @Schema(description = "즐겨찾기 여부", example = "true")
    private Boolean isFavorite;

    @Schema(description = "운동 프로그램 타입", allowableValues = {"D", "T", "I"})
    private ProgramType type;

    @Schema(description = "운동 프로그램 정보")
    private ProgramTypeInfoDto program;

    @Schema(description = "운동 프로그램 총 사용 통계")
    private TotalRecordDto totalRecord;

    @Schema(description = "운동 프로그램 총 사용 횟수", example = "10")
    private Integer usageCount;

    @Schema(description = "운동 프로그램 완주 횟수", example = "5")
    private Integer finishedCount;

    @Schema(description = "운동 프로그램 사용 기록")
    private List<UsageLogDto> usageLog;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TotalRecordDto {
        @Schema(description = "총 거리", example = "10.5")
        private Double distance;

        @Schema(description = "총 시간", example = "3600.0")
        private Double time;

        @Schema(description = "총 소모 칼로리", example = "200.0")
        private Double cal;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsageLogDto {
        @Schema(description = "운동 기록 ID", example = "1")
        private Long recordId;

        @Schema(description = "거리", example = "5.3")
        private Double distance;

        @Schema(description = "평균 페이스", example = "7.0")
        private Double averagePace;

        @Schema(description = "운동 시간", example = "1800.0")
        private Double time;

        @Schema(description = "운동 일자", example = "2023-05-01T10:15:30")
        private LocalDateTime date;

        @Schema(description = "소모 칼로리", example = "100.0")
        private Double cal;

        @Schema(description = "완주 여부", example = "true")
        private Boolean isFinished;
    }
}
