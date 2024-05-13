package com.gaenari.backend.domain.record.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordDto {
    @Schema(description = "연")
    private int year;

    @Schema(description = "월")
    private int month;

    @Schema(description = "일")
    private int day;

    @Schema(description = "하루에 한 운동 기록 리스트")
    private List<DailyRecordDto> dailyRecords;
}

