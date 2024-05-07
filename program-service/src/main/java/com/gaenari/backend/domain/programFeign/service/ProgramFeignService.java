package com.gaenari.backend.domain.programFeign.service;

import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDto;
import com.gaenari.backend.domain.programFeign.dto.ProgramDetailAboutRecordDto;

import java.util.List;

public interface ProgramFeignService {

    ProgramDetailAboutRecordDto getProgramDetailById(Long programId);

    Integer updateProgramUsageCount(Long programId);
}
