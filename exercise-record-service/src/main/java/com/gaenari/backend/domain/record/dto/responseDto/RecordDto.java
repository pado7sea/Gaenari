package com.gaenari.backend.domain.record.dto.responseDto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordDto {
    private int year;
    private int month;
    private int day;
    private List<DailyRecordDto> dailyRecords; // 하루에 한 운동 기록들

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyRecordDto {
        private Long recordId;
        private LocalDateTime recordDate; // 운동 일자
        private Double recordTime; // 운동 시간
        private Double recordDist; // 운동 거리
        private Double recordPace; // 운동 페이스
    }
}

