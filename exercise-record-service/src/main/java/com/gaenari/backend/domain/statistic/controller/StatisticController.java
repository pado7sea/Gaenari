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

    @Operation(summary = "전체 통계 조회 1 (test)", description = "운동기록 전체 순회돌아서 누적값 계산(아래거랑 값 같은지 비교용)")
    @GetMapping("/v1")
    public ResponseEntity<?> getAllStatistics(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId) {
        TotalStatisticDto statistic = statisticService.getWholeExerciseStatistics(memberId);

        return response.success(ResponseCode.STATISTIC_ALL_FETCHED, statistic);
    }

    @Operation(summary = "전체 통계 조회 2", description = "저장 되어있는 누적값 조회")
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
                                                  @Parameter(name = "연") @PathVariable(name = "year") int year,
                                                  @Parameter(name = "월") @PathVariable(name = "month") int month) {
        MonthStatisticDto statistic = statisticService.getMonthlyExerciseStatistics(memberId, year, month);

        return response.success(ResponseCode.STATISTIC_MONTH_FETCHED, statistic);
    }

    @Operation(summary = "주간 통계 조회", description = "정보를 보길 원하는 주간의 어느 날짜든 주면 됨(일~토). ex) 2024년 5월 5일(월) → 2024년 5월 4일(일) ~  5월11일(토)")
    @GetMapping("/week/{year}/{month}/{day}")
    public ResponseEntity<?> getWeeklyStatistics(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId,
                                                 @Parameter(name = "연") @PathVariable(name = "year") int year,
                                                 @Parameter(name = "월") @PathVariable(name = "month") int month,
                                                 @Parameter(name = "일") @PathVariable(name = "day") int day) {
        WeekStatisticDto statistic = statisticService.getWeeklyExerciseStatistics(memberId, year, month, day);

        return response.success(ResponseCode.STATISTIC_WEEK_FETCHED, statistic);
    }

}
