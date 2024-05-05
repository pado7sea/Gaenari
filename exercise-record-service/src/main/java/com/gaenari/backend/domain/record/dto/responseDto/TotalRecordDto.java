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
        private List<DailyRecordDto> records;
    }
}

