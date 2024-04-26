package com.gaenari.backend.domain.favorite.service.impl;

import com.gaenari.backend.domain.favorite.dto.responseDto.FavoriteListDto;
import com.gaenari.backend.domain.favorite.repository.FavoriteRepository;
import com.gaenari.backend.domain.favorite.service.FavoriteService;
import com.gaenari.backend.domain.program.dto.responseDto.IntervalInfo;
import com.gaenari.backend.domain.program.entity.Program;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Override
    public List<FavoriteListDto> getFavoriteList(Long memberId) {
        List<FavoriteListDto.ProgramDto> programDtos = convertToProgramDto(favoriteRepository.getFavoriteList(memberId));

        if (programDtos.isEmpty()) {
            return Collections.emptyList(); // 목록이 비어있다면 빈 리스트 반환
        }

        // 모든 ProgramDto를 담은 하나의 ProgramListDto 생성
        FavoriteListDto programListDto = new FavoriteListDto(programDtos);

        // 리스트에 ProgramListDto를 담아서 반환
        return Collections.singletonList(programListDto);
    }

    private List<FavoriteListDto.ProgramDto> convertToProgramDto(List<Program> programs) {
        return programs.stream()
                .map(this::convertToProgramDto)
                .collect(Collectors.toList());
    }


    private FavoriteListDto.ProgramDto convertToProgramDto(Program program) {
        FavoriteListDto.ProgramDto.ProgramInfo programInfo = convertToProgramInfo(program);

        return new FavoriteListDto.ProgramDto(
                program.getId(),
                program.getTitle(),
                program.getUsageCount(),
                program.getType(),
                programInfo
        );
    }

    private FavoriteListDto.ProgramDto.ProgramInfo convertToProgramInfo(Program program) {
        switch (program.getType()) {
            case D:  // 거리 목표 프로그램
                return new FavoriteListDto.ProgramDto.DistanceTargetProgramInfo(program.getTargetValue());

            case T:  // 시간 목표 프로그램
                return new FavoriteListDto.ProgramDto.TimeTargetProgramInfo(program.getTargetValue());

            case I:  // 인터벌 프로그램
                List<IntervalInfo.IntervalRange> ranges = program.getRanges().stream()
                        .map(range -> new IntervalInfo.IntervalRange(
                                range.getId(), range.isRunning(), range.getTime(), range.getSpeed()))
                        .collect(Collectors.toList());
                return new  FavoriteListDto.ProgramDto.IntervalProgramInfo(
                        program.getDuration(), program.getSetCount(), ranges);

            default:
                throw new IllegalStateException("Unexpected value: " + program.getType());
        }
    }
}
