package com.gaenari.backend.domain.program.dto.requestDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntervalRequestDto {
    private Double duration; // 인터벌 총 소요 시간 웜업, 쿨다운 추가
    private Integer setCount; // 세트 수
    private Integer rangeCount; // 세트 당 구간 수
    private List<RangeRequestDto> ranges;
}