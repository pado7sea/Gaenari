package com.gaenari.backend.domain.statistic.service;

import com.gaenari.backend.domain.statistic.dto.responseDto.MonthStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.StatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;

import java.util.List;

public interface StatisticService {

    /**
     * 저장된 회원의 누적 통계 값을 조회합니다.
     *
     * @param memberId 회원의 식별자입니다.
     * @return 누적 통계 정보를 담은 TotalStatisticDto 객체를 반환합니다.
     */
    TotalStatisticDto getTotalStatistics(String memberId);

    /**
     * 지정된 연도와 월에 대한 회원의 월간 운동 통계를 조회합니다.
     *
     * @param memberId 회원의 식별자입니다.
     * @param year     조회할 연도입니다.
     * @param month    조회할 월입니다.
     * @return 해당 월의 운동 통계를 담은 MonthStatisticDto 객체를 반환합니다.
     */
    MonthStatisticDto getMonthlyExerciseStatistics(String memberId, int year, int month);

    /**
     * 지정된 연도, 월, 일에 대한 회원의 주간 운동 통계를 조회합니다.
     *
     * @param memberId 회원의 식별자입니다.
     * @param year     조회할 연도입니다.
     * @param month    조회할 월입니다.
     * @param day      조회 시작 일입니다.
     * @return 해당 주의 운동 통계를 담은 WeekStatisticDto 객체를 반환합니다.
     */
    List<StatisticDto> getWeeklyExerciseStatistics(String memberId, int year, int month, int day);

}