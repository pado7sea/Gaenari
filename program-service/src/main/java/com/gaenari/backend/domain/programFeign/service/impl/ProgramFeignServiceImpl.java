package com.gaenari.backend.domain.programFeign.service.impl;

import com.gaenari.backend.domain.program.dto.responseDto.IntervalDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramTypeInfoDto;
import com.gaenari.backend.domain.program.dto.responseDto.RangeDto;
import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.domain.program.repository.ProgramRepository;
import com.gaenari.backend.domain.programFeign.dto.ProgramDetailAboutRecordDto;
import com.gaenari.backend.domain.programFeign.service.ProgramFeignService;
import com.gaenari.backend.global.exception.program.ProgramNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramFeignServiceImpl implements ProgramFeignService {

    private final ProgramRepository programRepository;

    @Override
    public ProgramDetailAboutRecordDto getProgramDetailById(Long programId) {
        Optional<Program> optionalProgram = programRepository.findById(programId);

        if (optionalProgram.isEmpty()) {
            throw new ProgramNotFoundException();
        }

        Program program = optionalProgram.get();

        // RangeDto 리스트로 변환
        var ranges = program.getRanges().stream()
                .map(range -> RangeDto.builder()
                        .id(range.getId())
                        .isRunning(range.getIsRunning())
                        .time(range.getTime())
                        .speed(range.getSpeed())
                        .build())
                .toList();

        return ProgramDetailAboutRecordDto.builder()
                .programId(program.getId())
                .programTitle(program.getTitle())
                .isFavorite(program.getIsFavorite())
                .type(program.getType())
                .program(ProgramTypeInfoDto.builder()
                        .targetValue(program.getTargetValue())
                        .intervalInfo(IntervalDto.builder()
                                .duration(program.getDuration())
                                .setCount(program.getSetCount())
                                .rangeCount(program.getRanges().size())
                                .ranges(ranges)
                                .build())
                        .build())
                .usageCount(program.getUsageCount())
                .build();
    }

    @Override
    public Integer updateProgramUsageCount(Long programId) {
        Optional<Program> optionalProgram = programRepository.findById(programId);
        if (optionalProgram.isPresent()) {
            Program program = optionalProgram.get();
            program.updateUsageCount(1);
            programRepository.save(program);

            return program.getUsageCount();
        } else {
            throw new ProgramNotFoundException();
        }
    }
}
