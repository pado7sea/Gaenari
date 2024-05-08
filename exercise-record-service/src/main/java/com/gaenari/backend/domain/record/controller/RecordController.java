package com.gaenari.backend.domain.record.controller;

import com.gaenari.backend.domain.record.dto.responseDto.MonthRecordDto;
import com.gaenari.backend.domain.record.dto.responseDto.RecordDto;
import com.gaenari.backend.domain.record.dto.responseDto.WeekRecordDto;
import com.gaenari.backend.domain.record.service.RecordService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Tag(name = "Record Controller", description = "Record Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {

    private final ApiResponse response;
    private final RecordService recordService;

    @Operation(summary = "전체 기록 조회", description = "전체 기록 조회")
    @GetMapping
    public ResponseEntity<?> getAllRecords(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId) {
        List<RecordDto> recordDtos = recordService.getWholeExerciseRecords(memberId);

        return response.success(ResponseCode.RECORD_ALL_FETCHED, recordDtos);
    }

    @Operation(summary = "월간 기록 조회", description = "월간 기록 조회")
    @GetMapping("/month/{year}/{month}")
    public ResponseEntity<?> getMonthlyRecords(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId,
                                               @PathVariable(name = "year") int year, @PathVariable(name = "month") int month) {
        MonthRecordDto recordDtos = recordService.getMonthlyExerciseRecords(memberId, year, month);

        return response.success(ResponseCode.RECORD_MONTH_FETCHED, recordDtos);
    }

    @Operation(summary = "주간 기록 조회", description = "주간 기록 조회")
    @GetMapping("/week/{year}/{month}/{day}")
    public ResponseEntity<?> getWeeklyRecords(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId,
                                              @PathVariable(name = "year") int year, @PathVariable(name = "month") int month, @PathVariable(name = "day") int day) {
        WeekRecordDto recordDtos = recordService.getWeeklyExerciseRecords(memberId, year, month, day);

        return response.success(ResponseCode.RECORD_WEEK_FETCHED, recordDtos);
    }

    @Operation(summary = "일일 기록 조회", description = "일일 기록 조회")
    @GetMapping("/date/{date}")
    public ResponseEntity<?> getDailyRecords(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId,
                                             @PathVariable(name = "date") String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<RecordDto> recordDtos = recordService.getDailyExerciseRecords(memberId, localDate);

        return response.success(ResponseCode.RECORD_DAY_FETCHED, recordDtos);
    }

}
