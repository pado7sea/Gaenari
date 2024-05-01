package com.gaenari.backend.domain.record.dto.responseDto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalRecordDto {
    private int year;
    private int month;
    private List<ExerciseRecordDto> exerciseRecords;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExerciseRecordDto {
        private String day; // 일 단위
        private List<DayRecordDto> records;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DayRecordDto {
        private Long recordId;
        private LocalDateTime recordDate; // 운동 일자
        private Double recordTime; // 운동 시간
        private Double recordDist; // 운동 거리
        private Double recordPace; // 운동 페이스
    }
}

