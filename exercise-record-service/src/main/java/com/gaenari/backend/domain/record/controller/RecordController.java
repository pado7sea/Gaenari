package com.gaenari.backend.domain.record.controller;

import com.gaenari.backend.domain.record.dto.responseDto.MonthRecordDto;
import com.gaenari.backend.domain.record.dto.responseDto.RecordDto;
import com.gaenari.backend.domain.record.dto.responseDto.WeekRecordDto;
import com.gaenari.backend.domain.record.service.RecordService;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Tag(name = "Record Controller", description = "Record Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {

    private final ApiResponseCustom response;
    private final RecordService recordService;

    @Operation(summary = "전체 기록 조회", description = "전체 기록이 없으면 빈 리스트 반환")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 기록 조회 성공", content = @Content(schema = @Schema(implementation = RecordDto.class))),
    })
    public ResponseEntity<?> getAllRecords(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId) {
        List<RecordDto> recordDtos = recordService.getWholeExerciseRecords(accountId);

        return response.success(ResponseCode.RECORD_ALL_FETCHED, recordDtos);
    }

    @Operation(summary = "월간 기록 조회", description = "월간 기록이 없으면 빈 리스트 반환")
    @GetMapping("/month/{year}/{month}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "월간 기록 조회 성공", content = @Content(schema = @Schema(implementation = MonthRecordDto.class))),
    })
    public ResponseEntity<?> getMonthlyRecords(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                               @Parameter(description = "연") @PathVariable(name = "year") int year,
                                               @Parameter(description = "월") @PathVariable(name = "month") int month) {
        MonthRecordDto recordDtos = recordService.getMonthlyExerciseRecords(accountId, year, month);

        return response.success(ResponseCode.RECORD_MONTH_FETCHED, recordDtos);
    }

    @Operation(summary = "주간 기록 조회", description = "주간 기록이 없으면 빈 리스트 반환. 정보를 보길 원하는 주간의 어느 날짜든 주면 됨(일~토). ex) 2024년 5월 5일(월) → 2024년 5월 4일(일) ~  5월11일(토)\"")
    @GetMapping("/week/{year}/{month}/{day}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주간 기록 조회 성공", content = @Content(schema = @Schema(implementation = WeekRecordDto.class))),
    })
    public ResponseEntity<?> getWeeklyRecords(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                              @Parameter(description = "연") @PathVariable(name = "year") int year,
                                              @Parameter(description = "월") @PathVariable(name = "month") int month,
                                              @Parameter(description = "일주일 중 아무 날짜") @PathVariable(name = "day") int day) {
        WeekRecordDto recordDtos = recordService.getWeeklyExerciseRecords(accountId, year, month, day);

        return response.success(ResponseCode.RECORD_WEEK_FETCHED, recordDtos);
    }

    @Operation(summary = "일일 기록 조회", description = "일일 기록이 없으면 빈 리스트 반환")
    @GetMapping("/date/{date}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일일 기록 조회 성공", content = @Content(schema = @Schema(implementation = RecordDto.class))),
    })
    public ResponseEntity<?> getDailyRecords(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                             @Parameter(description = "연월일 8글자") @PathVariable(name = "date") String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<RecordDto> recordDtos = recordService.getDailyExerciseRecords(accountId, localDate);

        return response.success(ResponseCode.RECORD_DAY_FETCHED, recordDtos);
    }

}
