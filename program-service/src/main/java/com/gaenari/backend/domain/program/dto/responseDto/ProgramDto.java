package com.gaenari.backend.domain.program.dto.responseDto;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDto {
    private Long programId;
    private String programTitle;
    private Boolean isFavorite; // 즐겨찾기 등록여부
    private int usageCount; // 운동 프로그램 총 사용횟수
    private int finishedCount; // 운동 프로그램 완주 횟수

    private ProgramType type; // enum: D(거리목표), T(시간목표), I(인터벌)
    private ProgramInfo program;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProgramInfo {
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
}

