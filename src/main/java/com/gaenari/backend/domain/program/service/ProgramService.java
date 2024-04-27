package com.gaenari.backend.domain.program.service;

import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.requestDto.ProgramUpdateDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramListDto;

import java.util.List;
import java.util.Optional;

public interface ProgramService {
    Long createProgram(ProgramCreateDto programDto);
//    Long updateProgram(Long programId, ProgramUpdateDto programDto);
    void deleteProgram(Long programId);
    Optional<List<ProgramListDto>> getProgramList();
//    Optional<List<ProgramListDto>> getProgramList(Long memberId);
    Optional<ProgramDetailDto> getProgramDetail(Long programId);

    // Optional<List<ProgramListDto>> getPopularPrograms(int limit);
    // Optional<List<ProgramListDto>> searchPrograms(String keyword, ProgramType type);
}
