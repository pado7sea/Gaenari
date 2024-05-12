package com.gaenari.backend.domain.programFeign.service;

import com.gaenari.backend.domain.programFeign.dto.ProgramDetailAboutRecordDto;

public interface ProgramFeignService {

    ProgramDetailAboutRecordDto getProgramDetailById(Long programId);

    Integer updateProgramUsageCount(Long programId);

}
