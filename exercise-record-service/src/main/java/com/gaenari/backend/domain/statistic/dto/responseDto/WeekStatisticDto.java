package com.gaenari.backend.domain.statistic.dto.responseDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeekStatisticDto {
    private Double time;   // 운동 총 시간
    private Double dist;   // 운동 총 거리
    private Double cal;    // 운동 총 소모 칼로리
    private Double pace;   // 운동 평균 페이스
    private Double heart;  // 운동 평균 심박수
}
