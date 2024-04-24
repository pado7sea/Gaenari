package com.gaenari.backend.domain.program.controller;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramListDto;
import com.gaenari.backend.domain.program.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProgramListDto>> getProgramList(@RequestParam Long memberId) {
        return programService.getProgramList(memberId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDetailDto> getProgramDetail(@PathVariable Long programId) {
        return programService.getProgramDetail(programId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/countFinish/{programId}")
    public ResponseEntity<Integer> countFinish(@PathVariable Long programId) {
        return programService.countFinish(programId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/popular")
    public ResponseEntity<List<ProgramListDto>> getPopularPrograms(@RequestParam int limit) {
        return programService.getPopularPrograms(limit)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProgramListDto>> searchPrograms(@RequestParam String keyword, @RequestParam ProgramType type) {
        return programService.searchPrograms(keyword, type)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}