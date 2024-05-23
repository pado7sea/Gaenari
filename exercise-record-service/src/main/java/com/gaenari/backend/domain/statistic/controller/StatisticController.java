package com.gaenari.backend.domain.statistic.controller;

import com.gaenari.backend.domain.statistic.dto.responseDto.MonthStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.StatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;
import com.gaenari.backend.domain.statistic.service.StatisticService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Statistic Controller", description = "Statistic Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic")
public class StatisticController {

    private final ApiResponseCustom response;
    private final StatisticService statisticService;

    @Operation(summary = "전체 통계 조회", description = "저장 되어있는 누적값 조회")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 통계 조회 성공", content = @Content(schema = @Schema(implementation = TotalStatisticDto.class))),
    })
    public ResponseEntity<?> getTotalStatistics(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId) {
        TotalStatisticDto statistic = statisticService.getTotalStatistics(accountId);

        return response.success(ResponseCode.STATISTIC_ALL_FETCHED, statistic);
    }

    @Operation(summary = "월간 통계 조회", description = "월간 통계 조회")
    @GetMapping("/month/{year}/{month}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "월간 통계 조회 성공", content = @Content(schema = @Schema(implementation = MonthStatisticDto.class))),
    })
    public ResponseEntity<?> getMonthlyStatistics(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                                  @Parameter(description = "연") @PathVariable(name = "year") int year,
                                                  @Parameter(description = "월") @PathVariable(name = "month") int month) {
        MonthStatisticDto statistic = statisticService.getMonthlyExerciseStatistics(accountId, year, month);

        return response.success(ResponseCode.STATISTIC_MONTH_FETCHED, statistic);
    }

    @Operation(summary = "주간 통계 조회", description = "정보를 보길 원하는 주간의 어느 날짜든 주면 됨(일~토). ex) 2024년 5월 5일(월) → 2024년 5월 4일(일) ~  5월11일(토)")
    @GetMapping("/week/{year}/{month}/{day}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주간 통계 조회 성공", content = @Content(schema = @Schema(implementation = StatisticDto.class))),
    })
    public ResponseEntity<?> getWeeklyStatistics(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                                 @Parameter(description = "연") @PathVariable(name = "year") int year,
                                                 @Parameter(description = "월") @PathVariable(name = "month") int month,
                                                 @Parameter(description = "일") @PathVariable(name = "day") int day) {
        List<StatisticDto> statistic = statisticService.getWeeklyExerciseStatistics(accountId, year, month, day);

        return response.success(ResponseCode.STATISTIC_WEEK_FETCHED, statistic);
    }

}
