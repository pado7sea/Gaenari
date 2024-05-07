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

@Tag(name = "Statistic Feign Controller", description = "Statistic Feign Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic/feign")
public class StatisticFeignController {

    private final StatisticService statisticService;

    @Operation(summary = "전체 통계 조회", description = "저장되어있는 누적값 조회")
    @GetMapping("/{memberId}")
    public TotalStatisticDto getTotalStatistics(@PathVariable(name = "memberId") String memberId) {
        TotalStatisticDto statistics = statisticService.getTotalStatistics(memberId);

        return statistics;
    }

}