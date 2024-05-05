package com.gaenari.backend.domain.record.dto.responseDto;

import lombok.*;

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


}

