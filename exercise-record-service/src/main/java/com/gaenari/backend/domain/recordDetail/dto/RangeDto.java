package com.gaenari.backend.domain.recordDetail.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RangeDto {  // 구간 정보
    private Long id;
    private Boolean isRunning;
    private Double time;
    private Double speed;
}