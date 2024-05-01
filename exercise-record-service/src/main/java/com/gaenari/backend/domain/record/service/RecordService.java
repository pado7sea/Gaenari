package com.gaenari.backend.domain.record.service;

import com.gaenari.backend.domain.record.dto.responseDto.MonthRecordDto;
import com.gaenari.backend.domain.record.dto.responseDto.RecordDto;
import com.gaenari.backend.domain.record.dto.responseDto.WeekRecordDto;

import java.time.LocalDate;
import java.util.List;

public interface RecordService {

    List<RecordDto> getWholeExerciseRecords(Long memberId);

    MonthRecordDto getMonthlyExerciseRecords(Long memberId, int year, int month);

    WeekRecordDto getWeeklyExerciseRecords(Long memberId, int year, int month, int day);

    List<RecordDto> getDailyExerciseRecords(Long memberId, LocalDate date);

}
