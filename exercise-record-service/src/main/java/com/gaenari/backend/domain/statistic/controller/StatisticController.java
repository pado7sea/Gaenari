package com.gaenari.backend.domain.statistic.controller;

import com.gaenari.backend.domain.statistic.dto.responseDto.MonthStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.WeekStatisticDto;
import com.gaenari.backend.domain.statistic.service.StatisticService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Statistic Controller", description = "Statistic Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic")
public class StatisticController {

    private final ApiResponse response;
    private final StatisticService statisticService;

    @Operation(summary = "전체 통계 조회 1", description = "운동기록 전체 순회돌아서 누적값 계산(아래거랑 값 같은지 비교용)")
    @GetMapping("/v1")
    public ResponseEntity<?> getAllStatistics(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId) {
        TotalStatisticDto statistic = statisticService.getWholeExerciseStatistics(memberId);

        return response.success(ResponseCode.STATISTIC_ALL_FETCHED, statistic);
    }

    @Operation(summary = "전체 통계 조회 2", description = "저장되어있는 누적값 조회")
    @GetMapping("/v2")
    public ResponseEntity<?> getTotalStatistics(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId) {
        TotalStatisticDto statistic = statisticService.getTotalStatistics(memberId);

        return response.success(ResponseCode.STATISTIC_ALL_FETCHED, statistic);
    }

//    @Operation(summary = "전체 통계 업데이트", description = "회원의 운동 기록을 저장할 때마다 누적 통계를 업데이트하는 방식")
//    @GetMapping
//    public ResponseEntity<?> updateExerciseStatistics() {
//        Long memberId = 1L;
//        TotalStatisticDto statistics = statisticService.updateExerciseStatistics(memberId,newRecord);
//
//        return response.success(ResponseCode.STATISTIC_ALL_FETCHED, statistics);
//    }

    @Operation(summary = "월간 통계 조회", description = "월간 통계 조회")
    @GetMapping("/month/{year}/{month}")
    public ResponseEntity<?> getMonthlyStatistics(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId,
                                                  @PathVariable(name = "year") int year, @PathVariable(name = "month") int month) {
        MonthStatisticDto statistic = statisticService.getMonthlyExerciseStatistics(memberId, year, month);

        return response.success(ResponseCode.STATISTIC_MONTH_FETCHED, statistic);
    }

    @Operation(summary = "주간 통계 조회", description = "주간 통계 조회")
    @GetMapping("/week/{year}/{month}/{day}")
    public ResponseEntity<?> getWeeklyStatistics(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId,
                                                 @PathVariable(name = "year") int year, @PathVariable(name = "month") int month, @PathVariable(name = "day") int day) {
        WeekStatisticDto statistic = statisticService.getWeeklyExerciseStatistics(memberId, year, month, day);

        return response.success(ResponseCode.STATISTIC_WEEK_FETCHED, statistic);
    }

}
