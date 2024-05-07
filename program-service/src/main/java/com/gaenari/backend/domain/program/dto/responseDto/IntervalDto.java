package com.gaenari.backend.domain.program.dto.responseDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntervalDto {
    private Double duration;  // 인터벌 총 소요 시간
    private Integer setCount;  // 세트 수
    private Integer rangeCount; // 세트 당 구간 수
    private List<RangeDto> ranges; // 구간 리스트
}
