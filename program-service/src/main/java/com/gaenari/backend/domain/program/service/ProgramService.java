package com.gaenari.backend.domain.program.service;

import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDto;

import java.util.List;

public interface ProgramService {

    Long createProgram(String memberId, ProgramCreateDto programDto);

    void deleteProgram(String memberId, Long programId);

    List<ProgramDto> getProgramList(String memberId);

    ProgramDetailDto getProgramDetail(String memberId, Long programId);

}