package com.gaenari.backend.domain.statistic.service;

import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.statistic.dto.responseDto.MonthStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.WeekStatisticDto;

public interface StatisticService {

    // 회원 ID에 해당하는 모든 운동 기록을 조회한 후 그 결과를 이용해 총 합계와 평균을 계산하는 방식
    TotalStatisticDto getWholeExerciseStatistics(String memberId);

    // 저장되어있는 누적값 조회
    TotalStatisticDto getTotalStatistics(String memberId);

    MonthStatisticDto getMonthlyExerciseStatistics(String memberId, int year, int month);

    WeekStatisticDto getWeeklyExerciseStatistics(String memberId, int year, int month, int day);

}
