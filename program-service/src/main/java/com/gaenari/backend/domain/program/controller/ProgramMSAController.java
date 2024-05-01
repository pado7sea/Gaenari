package com.gaenari.backend.domain.program.controller;

import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDto;
import com.gaenari.backend.domain.program.service.impl.ProgramServiceImpl;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Program MSA Controller", description = "Program MSA Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/program/msa")
public class ProgramMSAController {

    private final ApiResponse response;
    private final ProgramServiceImpl programService;

    @Operation(summary = "운동 프로그램 정보 조회", description = "운동 프로그램 정보 조회")
    @GetMapping("/{programId}")
    public ResponseEntity<?> getProgramDetail(@PathVariable(name = "programId") Long programId) {
//        Long memberId = 1L;
        ProgramDetailDto programDetail = programService.getProgramInfo(programId);

        return response.success(ResponseCode.PROGRAM_INFO_FETCHED, programDetail);
    }
    
}