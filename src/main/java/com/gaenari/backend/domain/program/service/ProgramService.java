package com.gaenari.backend.domain.program.service;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramListDto;
import com.gaenari.backend.domain.program.repository.custom.ProgramRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {

    private final ProgramRepositoryCustom programRepository;

    @Autowired
    public ProgramService(ProgramRepositoryCustom programRepository) {
        this.programRepository = programRepository;
    }

    public Optional<List<ProgramListDto>> getProgramList(Long memberId) {
        return programRepository.getProgramList(memberId);
    }

    public Optional<ProgramDetailDto> getProgramDetail(Long programId) {
        return programRepository.getProgramDetail(programId);
    }

    public Optional<Integer> countFinish(Long programId) {
        return programRepository.countFinish(programId);
    }

    public Optional<List<ProgramListDto>> getPopularPrograms(int limit) {
        return programRepository.getPopularPrograms(limit);
    }

    public Optional<List<ProgramListDto>> searchPrograms(String keyword, ProgramType type) {
        return programRepository.searchPrograms(keyword, type);
    }
}
