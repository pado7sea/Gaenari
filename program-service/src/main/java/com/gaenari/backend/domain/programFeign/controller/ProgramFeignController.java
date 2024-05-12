package com.gaenari.backend.domain.programFeign.controller;

import com.gaenari.backend.domain.programFeign.dto.ProgramDetailAboutRecordDto;
import com.gaenari.backend.domain.programFeign.service.impl.ProgramFeignServiceImpl;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Program Feign Controller", description = "Program Feign Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/program/feign")
public class ProgramFeignController {

    private final ApiResponse response;
    private final ProgramFeignServiceImpl programFeignService;

    @Operation(summary = "운동 프로그램 정보 조회", description = "운동 프로그램 정보 조회")
    @GetMapping("/{programId}")
    public ResponseEntity<?> getProgramDetail(@Parameter(description = "운동 프로그램 ID")  @PathVariable(name = "programId") Long programId) {
        ProgramDetailAboutRecordDto programDetailAboutRecordDto = programFeignService.getProgramDetailById(programId);

        return response.success(ResponseCode.PROGRAM_INFO_FETCHED, programDetailAboutRecordDto);
    }

    @Operation(summary = "운동 프로그램 사용 횟수 1 증가", description = "운동 프로그램 사용 횟수 1 증가")
    @PutMapping("/use/{programId}")
    public ResponseEntity<?> updateProgramUsageCount(@Parameter(description = "운동 프로그램 ID")  @PathVariable(name = "programId") Long programId) {
        Integer count = programFeignService.updateProgramUsageCount(programId);

        return response.success(ResponseCode.PROGRAM_UPDATED, count);
    }

}