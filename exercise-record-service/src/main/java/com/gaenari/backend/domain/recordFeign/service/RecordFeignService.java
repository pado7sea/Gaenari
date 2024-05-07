package com.gaenari.backend.domain.recordFeign.service;

import com.gaenari.backend.domain.program.dto.ProgramDetailDto;

import java.util.List;

public interface RecordFeignService {

    List<ProgramDetailDto.UsageLogDto> getRecordsByProgramId(Long programId);

}
