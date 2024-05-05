package com.gaenari.backend.domain.programFeign.controller;

import com.gaenari.backend.domain.programFeign.dto.ProgramDetailAboutRecordDto;
import com.gaenari.backend.domain.programFeign.service.impl.ProgramFeignServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Program Feign Controller", description = "Program Feign Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/program/feign")
public class ProgramFeignController {

    private final ProgramFeignServiceImpl programFeignService;

    @Operation(summary = "운동 프로그램 정보 조회", description = "운동 프로그램 정보 조회")
    @GetMapping("/{programId}")
    public ProgramDetailAboutRecordDto getProgramDetail(@PathVariable(name = "programId") Long programId) {

        return programFeignService.getProgramDetailById(programId);
    }

    @Operation(summary = "운동 프로그램 사용 횟수 1 증가", description = "운동 프로그램 사용 횟수 1 증가")
    @GetMapping("/use/{programId}")
    public Integer updateProgramUsageCount(@PathVariable(name = "programId") Long programId) {

        return programFeignService.updateProgramUsageCount(programId);
    }
    
}