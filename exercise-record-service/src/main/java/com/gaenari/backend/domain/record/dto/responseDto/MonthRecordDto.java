package com.gaenari.backend.domain.record.dto.responseDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthRecordDto {
    private int year;
    private int month;
    private List<RecordDto> exerciseRecords;
}


