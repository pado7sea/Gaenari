package com.gaenari.backend.domain.favorite.service.impl;

import com.gaenari.backend.domain.favorite.dto.responseDto.FavoriteDto;
import com.gaenari.backend.domain.favorite.repository.FavoriteRepository;
import com.gaenari.backend.domain.favorite.service.FavoriteService;
import com.gaenari.backend.domain.program.dto.responseDto.IntervalInfo;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDto;
import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.global.exception.favorite.FavoriteCreateException;
import com.gaenari.backend.global.exception.favorite.FavoriteDeleteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Override
    public List<FavoriteDto> getFavoriteList(Long memberId) {
        List<FavoriteDto> programDtos = convertToProgramDto(favoriteRepository.getFavoriteList(memberId));

        // 리스트에 ProgramListDto를 담아서 반환
        return programDtos;
    }

    private List<FavoriteDto> convertToProgramDto(List<Program> programs) {
        return programs.stream()
                .map(this::convertToProgramDto)
                .collect(Collectors.toList());
    }

    private FavoriteDto convertToProgramDto(Program program) {
        FavoriteDto.ProgramInfo programInfo = convertToProgramInfo(program);

        return new FavoriteDto(
                program.getId(),
                program.getTitle(),
                program.getUsageCount(),
                program.getType(),
                programInfo
        );
    }

    private FavoriteDto.ProgramInfo convertToProgramInfo(Program program) {
        switch (program.getType()) {
            case D:  // 거리 목표 프로그램
                return new FavoriteDto.ProgramInfo(program.getTargetValue(), null);

            case T:  // 시간 목표 프로그램
                return new FavoriteDto.ProgramInfo(program.getTargetValue(), null);

            case I:  // 인터벌 프로그램
                List<ProgramDto.RangeDto> ranges = program.getRanges().stream()
                        .map(range -> new ProgramDto.RangeDto(
                                range.getId(), range.isRunning(), range.getTime(), range.getSpeed()))
                        .collect(Collectors.toList());

                int setCount = program.getSetCount();
                int rangeCount = ranges.size();
                int setDuration = ranges.stream().mapToInt(ProgramDto.RangeDto::getTime).sum(); // 각 range의 시간의 합

                ProgramDto.IntervalDto intervalDto = new ProgramDto.IntervalDto(setDuration, setCount, rangeCount, ranges);
                return new FavoriteDto.ProgramInfo(null, intervalDto);

            default:
                throw new IllegalStateException("Unexpected value: " + program.getType());
        }
    }

    @Override
    public Boolean registerFavorite(Long programId) {
        Program program = favoriteRepository.findById(programId).orElseThrow(FavoriteCreateException::new);

        program.updateIsFavorite(true);
        favoriteRepository.save(program);

        return true;
    }

    @Override
    public Boolean clearFavorite(Long programId) {
        Program program = favoriteRepository.findById(programId).orElseThrow(FavoriteDeleteException::new);

        program.updateIsFavorite(false);
        favoriteRepository.save(program);

        return true;
    }
}
