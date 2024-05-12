package com.gaenari.backend.domain.recordFeign.controller;

import com.gaenari.backend.domain.client.program.dto.ProgramDetailDto;
import com.gaenari.backend.domain.recordFeign.service.RecordFeignService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Record Feign Controller", description = "Record Feign Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/record/feign")
public class RecordFeignController {

    private final ApiResponse response;
    private final RecordFeignService recordFeignService;

    @Operation(summary = "[Feign] 프로그램 운동 기록 조회", description = "프로그램 운동 기록 조회")
    @GetMapping("/{programId}")
    public ResponseEntity<?> getUsageLog(@Parameter(description = "프로그램 ID") @PathVariable(name = "programId") Long programId) {

        List<ProgramDetailDto.UsageLogDto> usageLogDtos = recordFeignService.getRecordsByProgramId(programId);
        return response.success(ResponseCode.RECORD_PROGRAM_FETCHED, usageLogDtos);
    }

    @Operation(summary = "[Feign] 운동 기록의 도전과제 ID 리스트 조회", description = "운동 기록 ID로 도전과제 ID 리스트 반환")
    @GetMapping("/recordChallenge/{memberId}/{recordId}")
    public ResponseEntity<?> getChallengeIdsByRecordId(@Parameter(description = "회원 ID") @PathVariable(name = "memberId") String memberId,
                                                       @Parameter(description = "운동 기록 ID") @PathVariable(name = "recordId") Long recordId) {
        List<Integer> challengeIds = recordFeignService.getChallengeIdsByRecordId(memberId, recordId);

        return response.success(ResponseCode.RECORD_CHALLENGE_FETCHED, challengeIds);
    }

}
