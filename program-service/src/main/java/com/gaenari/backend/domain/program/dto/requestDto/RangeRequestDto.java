package com.gaenari.backend.domain.program.dto.requestDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RangeRequestDto {
    private Boolean isRunning; // true:뛰는시간, false:걷는시간
    private Double time; // 단위: sec
    private Double speed; // 단위: km/h
}