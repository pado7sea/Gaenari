package com.gaenari.backend.domain.record.service;

import com.gaenari.backend.domain.record.dto.responseDto.MonthRecordDto;
import com.gaenari.backend.domain.record.dto.responseDto.RecordDto;
import com.gaenari.backend.domain.record.dto.responseDto.WeekRecordDto;

import java.time.LocalDate;
import java.util.List;

public interface RecordService {

    /**
     * 사용자의 모든 운동 기록을 조회합니다.
     *
     * @param accountId 사용자의 식별자입니다.
     * @return 사용자의 모든 운동 기록 목록을 반환합니다.
     */
    List<RecordDto> getWholeExerciseRecords(String accountId);

    /**
     * 지정된 연도와 월에 대한 사용자의 월간 운동 기록을 조회합니다.
     *
     * @param accountId 사용자의 식별자입니다.
     * @param year 조회할 연도입니다.
     * @param month 조회할 월입니다.
     * @return 해당 월의 운동 기록을 포함한 MonthRecordDto를 반환합니다.
     */
    MonthRecordDto getMonthlyExerciseRecords(String accountId, int year, int month);

    /**
     * 지정된 연도, 월, 일에 대한 사용자의 주간 운동 기록을 조회합니다.
     *
     * @param accountId 사용자의 식별자입니다.
     * @param year 조회할 연도입니다.
     * @param month 조회할 월입니다.
     * @param day 조회 시작 일입니다.
     * @return 해당 주의 운동 기록을 포함한 WeekRecordDto를 반환합니다.
     */
    WeekRecordDto getWeeklyExerciseRecords(String accountId, int year, int month, int day);

    /**
     * 지정된 날짜에 대한 사용자의 일일 운동 기록을 조회합니다.
     *
     * @param accountId 사용자의 식별자입니다.
     * @param date 조회할 날짜입니다.
     * @return 해당 날짜의 운동 기록 목록을 반환합니다.
     */
    List<RecordDto> getDailyExerciseRecords(String accountId, LocalDate date);

}