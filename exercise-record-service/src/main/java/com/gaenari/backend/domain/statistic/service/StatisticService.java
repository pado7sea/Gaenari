package com.gaenari.backend.domain.statistic.service;

import com.gaenari.backend.domain.statistic.dto.responseDto.MonthStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.WeekStatisticDto;

public interface StatisticService {

    TotalStatisticDto getWholeExerciseStatistics(Long memberId);

    MonthStatisticDto getMonthlyExerciseStatistics(Long memberId, int year, int month);

    WeekStatisticDto getWeeklyExerciseStatistics(Long memberId, int year, int month, int day);

}
