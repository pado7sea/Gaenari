package com.gaenari.backend.domain.client.program.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntervalInfo {
    @Schema(description = "인터벌 총 소요 시간", example = "150")
    private int duration;

    @Schema(description = "세트 수", example = "5")
    private int setCount;

    @Schema(description = "세트 당 구간 수", example = "3")
    private int rangeCount;

    @Schema(description = "구간 리스트")
    private List<IntervalRange> ranges;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntervalRange { // 구간 정보
        @Schema(description = "구간 ID", example = "1")
        private Long id;

        @Schema(description = "달리기 여부", example = "true")
        private Boolean isRunning;

        @Schema(description = "구간 시간 (sec)", example = "300")
        private int time;

        @Schema(description = "구간 속도 (km/h)", example = "6")
        private double speed;
    }
    
}
