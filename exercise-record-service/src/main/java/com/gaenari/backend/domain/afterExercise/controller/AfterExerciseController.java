package com.gaenari.backend.domain.afterExercise.controller;

import com.gaenari.backend.domain.afterExercise.dto.requestDto.SaveExerciseRecordDto;
import com.gaenari.backend.domain.afterExercise.service.AfterExerciseService;
import com.gaenari.backend.domain.record.dto.responseDto.RecordDto;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "After Exercise Controller", description = "After Exercise Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/exercise")
public class AfterExerciseController {

    private final ApiResponseCustom response;
    private final AfterExerciseService afterExerciseService;

    @Transactional
    @Operation(summary = "최종 운동 기록 저장", description = "최종 운동 기록 저장")
    @PostMapping("/save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최종 운동 기록 저장 성공", content = @Content(schema = @Schema(implementation = Long.class))),
    })
    public ResponseEntity<?> saveExerciseRecord(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                                @Valid @RequestBody SaveExerciseRecordDto exerciseDto) {

        log.info("SaveExerciseRecordDto: {}", exerciseDto);

        // 프로그램 사용 횟수 1 증가
        afterExerciseService.updateProgramUsageCount(accountId, exerciseDto);

        // 운동 기록 저장 -> 미션
        Long exerciseId = afterExerciseService.saveExerciseRecord(accountId, exerciseDto);

        // 누적 통계 업데이트 -> 업적
        afterExerciseService.updateExerciseStatistics(accountId, exerciseDto);

        return response.success(ResponseCode.EXERCISE_RECORD_SAVE_SUCCESS, exerciseId);
    }

}
