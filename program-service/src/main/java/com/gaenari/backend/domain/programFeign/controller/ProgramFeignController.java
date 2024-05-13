package com.gaenari.backend.domain.programFeign.controller;

import com.gaenari.backend.domain.programFeign.dto.ProgramDetailAboutRecordDto;
import com.gaenari.backend.domain.programFeign.service.impl.ProgramFeignServiceImpl;
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

@Tag(name = "Program Feign Controller", description = "Program Feign Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/program/feign")
public class ProgramFeignController {

    private final ApiResponseCustom response;
    private final ProgramFeignServiceImpl programFeignService;

    @Operation(summary = "운동 프로그램 정보 조회", description = "운동 프로그램 정보 조회")
    @GetMapping("/{programId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "운동 프로그램 정보 조회 성공", content = @Content(schema = @Schema(implementation = ProgramDetailAboutRecordDto.class))),
    })
    public ResponseEntity<?> getProgramDetail(@Parameter(description = "운동 프로그램 ID")  @PathVariable(name = "programId") Long programId) {
        ProgramDetailAboutRecordDto programDetailAboutRecordDto = programFeignService.getProgramDetailById(programId);

        return response.success(ResponseCode.PROGRAM_INFO_FETCHED, programDetailAboutRecordDto);
    }

    @Operation(summary = "운동 프로그램 사용 횟수 1 증가", description = "운동 프로그램 사용 횟수 1 증가")
    @PutMapping("/use/{programId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "운동 프로그램 사용 횟수 1 증가 성공", content = @Content(schema = @Schema(implementation = Integer.class))),
    })
    public ResponseEntity<?> updateProgramUsageCount(@Parameter(description = "운동 프로그램 ID")  @PathVariable(name = "programId") Long programId) {
        Integer count = programFeignService.updateProgramUsageCount(programId);

        return response.success(ResponseCode.PROGRAM_UPDATED, count);
    }

}