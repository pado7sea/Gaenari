package com.gaenari.backend.domain.client.program.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntervalInfo {
    private int duration;  // 인터벌 총 소요 시간
    private int setCount;  // 세트 수
    private int rangeCount; // 세트 당 구간 수
    private List<IntervalRange> ranges; // 구간 리스트

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntervalRange { // 구간 정보
        private Long id;
        private Boolean isRunning;
        private int time;
        private int speed;
    }
    
}