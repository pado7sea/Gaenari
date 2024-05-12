package com.gaenari.backend.domain.afterExercise.controller;

import com.gaenari.backend.domain.afterExercise.dto.requestDto.SaveExerciseRecordDto;
import com.gaenari.backend.domain.afterExercise.service.AfterExerciseService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    private final ApiResponse response;
    private final AfterExerciseService afterExerciseService;

    @Transactional
    @Operation(summary = "최종 운동 기록 저장", description = "최종 운동 기록 저장")
    @PostMapping("/save")
    public ResponseEntity<?> saveExerciseRecord(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId,
                                                @Valid @RequestBody SaveExerciseRecordDto exerciseDto) {

        log.info("SaveExerciseRecordDto: {}", exerciseDto);

        // 프로그램 사용 횟수 1 증가
        afterExerciseService.updateProgramUsageCount(memberId, exerciseDto);

        // 운동 기록 저장 -> 미션
        Long exerciseId = afterExerciseService.saveExerciseRecord(memberId, exerciseDto);

        // 누적 통계 업데이트 -> 업적
        afterExerciseService.updateExerciseStatistics(memberId, exerciseDto);

        return response.success(ResponseCode.EXERCISE_RECORD_SAVE_SUCCESS, exerciseId);
    }

}
