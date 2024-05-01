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
    private ProgramDto program;

    //    private TotalRecordDto totalRecord;
    private int usageCount; // 운동 프로그램 총 사용횟수
//    private int finishedCount; // 운동 프로그램 완주 횟수
//    private List<UsageLogDto> usageLog;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProgramDto {
        private Double targetValue;
        private IntervalDto intervalInfo;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntervalDto {
        private Integer duration;   // 인터벌 총 소요 시간
        private Integer setCount;   // 세트 수
        private Integer rangeCount;  // 세트 당 구간 수
        private List<RangeDto> ranges; // 구간 리스트
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RangeDto {  // 구간 정보
        private Long id;
        private Boolean isRunning;
        private Integer time;
        private Integer speed;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TotalRecordDto {
        private double distance;
        private long time;
        private int cal;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsageLogDto {
        private int exerciseId;
        private double distance;
        private int averagePace;
        private long time;
        private LocalDateTime date;
        private int cal;
        private Boolean isFinished;
    }

}
