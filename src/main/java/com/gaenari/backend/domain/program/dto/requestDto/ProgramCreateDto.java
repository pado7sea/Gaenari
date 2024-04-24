package com.gaenari.backend.domain.program.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class ProgramCreateDto {
    private String programTitle;
    private String programType; // enum: D(거리목표), T(시간목표), I(인터벌)
    private int programTargetValue; // 거리목표/시간목표 수치. type이 I인 경우 -1
    private IntervalDto interval;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntervalDto {
        private int duration; // 인터벌 총 소요 시간 웜업, 쿨다운 추가
        private int setCount; // 세트 수
        private int rangeCount; // 세트 당 구간 수
        private List<RangeDto> ranges;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RangeDto {
        private boolean isRunning; // true:뛰는시간, false:걷는시간
        private int time; // 단위: sec
        private int speed; // 단위: km/h
    }
}
