package com.gaenari.backend.domain.program.service;

import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDto;

import java.util.List;

public interface ProgramService {

    Long createProgram(Long memberId, ProgramCreateDto programDto);

    void deleteProgram(Long memberId, Long programId);

    List<ProgramDto> getProgramList(Long memberId);

    ProgramDetailDto getProgramDetail(Long memberId, Long programId);

}
