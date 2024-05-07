package com.gaenari.backend.domain.program.controller;

import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDto;
import com.gaenari.backend.domain.program.service.impl.ProgramServiceImpl;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Program Controller", description = "Program Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/program")
public class ProgramController {

    private final ApiResponse response;
    private final ProgramServiceImpl programService;

    @Operation(summary = "운동 프로그램 목록 조회", description = "운동 프로그램 목록 조회")
    @GetMapping
    public ResponseEntity<?> getAllPrograms(@RequestHeader("User-Info") Long memberId) {
        List<ProgramDto> programList = programService.getProgramList(memberId);

        return response.success(ResponseCode.PROGRAM_LIST_FETCHED, programList);
    }

    @Operation(summary = "운동 프로그램 상세 조회", description = "운동 프로그램 상세 조회")
    @GetMapping("/{programId}")
    public ResponseEntity<?> getProgramDetail(@RequestHeader("User-Info") Long memberId, @PathVariable(name = "programId") Long programId) {
        ProgramDetailDto programDetail = programService.getProgramDetail(memberId, programId);

        return response.success(ResponseCode.PROGRAM_INFO_FETCHED, programDetail);
    }

    @Operation(summary = "운동 프로그램 생성", description = "운동 프로그램 생성")
    @PostMapping
    public ResponseEntity<?> createProgram(@Parameter(description = "회원 식별자 아이디") @RequestHeader("User-Info") Long memberId, @Valid @RequestBody ProgramCreateDto programDto) {
        Long programId = programService.createProgram(memberId, programDto);

        return response.success(ResponseCode.PROGRAM_CREATED, programId);
    }

    @Operation(summary = "운동 프로그램 삭제", description = "운동 프로그램 삭제")
    @DeleteMapping("/{programId}")
    public ResponseEntity<?> deleteProgram(@Parameter(description = "회원 식별자 아이디") @RequestHeader("User-Info") Long memberId, @PathVariable(name = "programId") Long programId) {
        programService.deleteProgram(memberId, programId);

        return response.success(ResponseCode.PROGRAM_DELETED);
    }

    @GetMapping("/health_check")
    public String healthCheck() {
        return "It's working now";
    }
}