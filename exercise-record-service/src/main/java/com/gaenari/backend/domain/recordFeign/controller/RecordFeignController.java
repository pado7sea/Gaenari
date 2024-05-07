package com.gaenari.backend.domain.recordFeign.controller;

import com.gaenari.backend.domain.client.program.dto.ProgramDetailDto;
import com.gaenari.backend.domain.recordFeign.service.RecordFeignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

    private final RecordFeignService recordFeignService;

    @Operation(summary = "프로그램 운동 기록 조회", description = "프로그램 운동 기록 조회")
    @GetMapping("/{programId}")
    public List<ProgramDetailDto.UsageLogDto> getUsageLog(@PathVariable(name = "programId") Long programId){
     return recordFeignService.getRecordsByProgramId(programId);
    }

}
