package com.gaenari.backend.domain.statistic.controller;

import com.gaenari.backend.domain.statistic.dto.responseDto.MonthStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.WeekStatisticDto;
import com.gaenari.backend.domain.statistic.service.StatisticService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Statistic Controller", description = "Statistic Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic")
public class StatisticController {

    private final ApiResponse response;
    private final StatisticService statisticService;

    @Operation(summary = "전체 통계 조회", description = "전체 통계 조회")
    @GetMapping
    public ResponseEntity<?> getAllStatistics() {
        Long memberId = 1L;
        TotalStatisticDto statistics = statisticService.getWholeExerciseStatistics(memberId);

        return response.success(ResponseCode.STATISTIC_ALL_FETCHED, statistics);
    }

    @Operation(summary = "월간 통계 조회", description = "월간 통계 조회")
    @GetMapping("/month/{year}/{month}")
    public ResponseEntity<?> getMonthlyStatistics(@PathVariable(name = "year") int year, @PathVariable(name = "month") int month) {
        Long memberId = 1L;
        MonthStatisticDto statistics = statisticService.getMonthlyExerciseStatistics(memberId, year, month);

        return response.success(ResponseCode.STATISTIC_MONTH_FETCHED, statistics);
    }

    @Operation(summary = "주간 통계 조회", description = "주간 통계 조회")
    @GetMapping("/week/{year}/{month}/{day}")
    public ResponseEntity<?> getWeeklyStatistics(@PathVariable(name = "year") int year, @PathVariable(name = "month") int month, @PathVariable(name = "day") int day) {
        Long memberId = 1L;
        WeekStatisticDto statistics = statisticService.getWeeklyExerciseStatistics(memberId, year, month, day);

        return response.success(ResponseCode.STATISTIC_WEEK_FETCHED, statistics);
    }

}
