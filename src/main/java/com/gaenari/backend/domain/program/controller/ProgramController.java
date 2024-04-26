package com.gaenari.backend.domain.program.controller;

import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.requestDto.ProgramUpdateDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramListDto;
import com.gaenari.backend.domain.program.service.impl.ProgramServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Program Controller", description = "Program Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/program-service/program")
public class ProgramController {

    private final ProgramServiceImpl programService;

    @Operation(summary = "운동 프로그램 목록 조회", description = "운동 프로그램 목록 조회")
    @GetMapping
    public ResponseEntity<List<ProgramListDto>> getAllPrograms() {
        Optional<List<ProgramListDto>> programList = programService.getProgramList();
        return programList
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "운동 프로그램 상세 조회", description = "운동 프로그램 상세 조회")
    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDetailDto> getProgramDetail(@PathVariable(name="programId") Long programId) {
        Optional<ProgramDetailDto> programDetail = programService.getProgramDetail(programId);
        return programDetail
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "운동 프로그램 생성", description = "운동 프로그램 생성")
    @PostMapping
    public ResponseEntity<Long> createProgram(@RequestBody ProgramCreateDto programDto) {
        Long programId = programService.createProgram(programDto);
        return ResponseEntity.ok(programId);
    }

//    @Operation(summary = "운동 프로그램 수정", description = "운동 프로그램 수정")
//    @PutMapping
//    public ResponseEntity<Long> updateProgram(@RequestBody ProgramUpdateDto programDto) {
//        Long updatedProgramId = programService.updateProgram(programDto);
//        return ResponseEntity.ok(updatedProgramId);
//    }

    @Operation(summary = "운동 프로그램 삭제", description = "운동 프로그램 삭제")
    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable(name="programId") Long programId) {
        programService.deleteProgram(programId);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/popular")
//    public ResponseEntity<List<ProgramListDto>> getPopularPrograms(@RequestParam int limit) {
//        return programService.getPopularPrograms(limit)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<List<ProgramListDto>> searchPrograms(@RequestParam String keyword, @RequestParam ProgramType type) {
//        return programService.searchPrograms(keyword, type)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
}