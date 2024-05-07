package com.gaenari.backend.domain.program.dto.responseDto;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RangeDto { // 구간 정보
    private Long id;
    private Boolean isRunning;  // true:뛰는시간, false:걷는시간
    private Double time;  // 단위: sec
    private Double speed; // 단위: km/h
}
