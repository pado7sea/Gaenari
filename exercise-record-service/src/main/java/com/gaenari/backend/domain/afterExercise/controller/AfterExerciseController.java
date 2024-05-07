package com.gaenari.backend.domain.afterExercise.controller;

import com.gaenari.backend.domain.afterExercise.dto.requestDto.SaveExerciseRecordDto;
import com.gaenari.backend.domain.afterExercise.service.AfterExerciseService;
import com.gaenari.backend.domain.record.service.RecordService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getAllPrograms(@Valid @RequestBody SaveExerciseRecordDto exerciseDto) {
        Long memberId = 1L;
        // 누적 통계 업데이트 -> 업적
        afterExerciseService.updateExerciseStatistics(memberId, exerciseDto);

        // 프로그램 사용 횟수 1 증가
        afterExerciseService.updateProgramUsageCount(memberId, exerciseDto);

        // 운동 기록 저장 -> 미션
        Long exerciseId = afterExerciseService.saveExerciseRecord(memberId, exerciseDto);

        return response.success(ResponseCode.EXERCISE_RECORD_SAVE_SUCCESS, exerciseId);
    }


}
