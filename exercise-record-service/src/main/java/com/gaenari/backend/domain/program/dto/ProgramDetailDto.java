package com.gaenari.backend.domain.program.dto;

import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDetailDto {
    private Long programId;
    private String programTitle;
    private Boolean isFavorite;

    private ProgramType type;
    private ProgramTypeInfoDto program;

    private TotalRecordDto totalRecord; // 운동 프로그램 총 사용 통계
    private Integer usageCount; // 운동 프로그램 총 사용횟수
    private Integer finishedCount; // 운동 프로그램 완주 횟수
    private List<UsageLogDto> usageLog; // 운동 프로그램 사용 기록

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TotalRecordDto {
        private Double distance;
        private Double time;
        private Double cal;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsageLogDto {
        private Long recordId;
        private Double distance;
        private Double averagePace;
        private Double time;
        private LocalDateTime date;
        private Double cal;
        private Boolean isFinished;
    }

}
