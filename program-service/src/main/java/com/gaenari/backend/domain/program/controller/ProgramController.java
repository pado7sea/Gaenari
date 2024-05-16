package com.gaenari.backend.domain.program.controller;

import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDto;
import com.gaenari.backend.domain.program.service.impl.ProgramServiceImpl;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    private final ApiResponseCustom response;
    private final ProgramServiceImpl programService;

    @Operation(summary = "운동 프로그램 목록 조회", description = "운동 프로그램 목록 조회")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "운동 프로그램 목록 조회 성공", content = @Content(schema = @Schema(implementation = ProgramDto.class))),
    })
    public ResponseEntity<?> getAllPrograms(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId) {
        List<ProgramDto> programList = programService.getProgramList(accountId);

        return response.success(ResponseCode.PROGRAM_LIST_FETCHED, programList);
    }

    @Operation(summary = "운동 프로그램 상세 조회", description = "운동 프로그램 상세 조회")
    @GetMapping("/{programId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "운동 프로그램 상세 조회 성공", content = @Content(schema = @Schema(implementation = ProgramDetailDto.class))),
    })
    public ResponseEntity<?> getProgramDetail(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                              @Parameter(description = "운동 프로그램 ID") @PathVariable(name = "programId") Long programId) {
        ProgramDetailDto programDetail = programService.getProgramDetail(accountId, programId);

        return response.success(ResponseCode.PROGRAM_INFO_FETCHED, programDetail);
    }

    @Operation(summary = "운동 프로그램 생성", description = "운동 프로그램 생성")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "운동 프로그램 생성 성공", content = @Content(schema = @Schema(implementation = Long.class))),
    })
    public ResponseEntity<?> createProgram(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                           @Valid @RequestBody ProgramCreateDto programDto) {
        Long programId = programService.createProgram(accountId, programDto);

        return response.success(ResponseCode.PROGRAM_CREATED, programId);
    }

    @Operation(summary = "운동 프로그램 삭제", description = "운동 프로그램 삭제")
    @DeleteMapping("/{programId}")
    public ResponseEntity<?> deleteProgram(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                           @Parameter(description = "운동 프로그램 ID") @PathVariable(name = "programId") Long programId) {
        programService.deleteProgram(accountId, programId);

        return response.success(ResponseCode.PROGRAM_DELETED);
    }

    @GetMapping("/health_check")
    public String healthCheck() {
        return "It's working now";
    }
}