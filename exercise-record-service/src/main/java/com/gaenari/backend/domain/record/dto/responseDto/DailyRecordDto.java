package com.gaenari.backend.domain.record.dto.responseDto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyRecordDto {
    private Long recordId;
    private LocalDateTime recordDate; // 운동 일자
    private Double recordTime; // 운동 시간
    private Double recordDist; // 운동 거리
    private Double recordPace; // 운동 페이스
}
