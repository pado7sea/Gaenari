package com.gaenari.backend.domain.favorite.service.impl;

import com.gaenari.backend.domain.favorite.dto.responseDto.FavoriteDto;
import com.gaenari.backend.domain.favorite.repository.FavoriteRepository;
import com.gaenari.backend.domain.favorite.service.FavoriteService;
import com.gaenari.backend.domain.program.dto.responseDto.IntervalDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramTypeInfoDto;
import com.gaenari.backend.domain.program.dto.responseDto.RangeDto;
import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.global.exception.favorite.FavoriteCreateException;
import com.gaenari.backend.global.exception.favorite.FavoriteDeleteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    // 즐겨찾기 목록 조회
    @Override
    public List<FavoriteDto> getFavoriteList(Long memberId) {
        return favoriteRepository.getFavoriteList(memberId).stream()
                .map(program -> {
                    ProgramTypeInfoDto programTypeInfoDto = convertToProgramTypeInfoDto(program);

                    return FavoriteDto.builder()
                            .programId(program.getId())
                            .programTitle(program.getTitle())
                            .usageCount(program.getUsageCount())
                            .finishedCount(0)
                            .type(program.getType())
                            .program(programTypeInfoDto)
                            .build();
                })
                .toList();
    }

    private ProgramTypeInfoDto convertToProgramTypeInfoDto(Program program) {
        switch (program.getType()) {
            case D:  // 거리 목표 프로그램
            case T:  // 시간 목표 프로그램
                return ProgramTypeInfoDto.builder()
                        .targetValue(program.getTargetValue())
                        .build();

            case I:  // 인터벌 프로그램
                List<RangeDto> ranges = program.getRanges().stream()
                        .map(range -> RangeDto.builder()
                                .id(range.getId())
                                .isRunning(range.getIsRunning())
                                .time(range.getTime())
                                .speed(range.getSpeed())
                                .build())
                        .toList();

                int setCount = program.getSetCount();
                int rangeCount = ranges.size();
                Double setDuration = ranges.stream().mapToDouble(RangeDto::getTime).sum(); // 각 range의 시간의 합

                return ProgramTypeInfoDto.builder()
                        .intervalInfo(IntervalDto.builder()
                                .duration(setDuration)
                                .setCount(setCount)
                                .rangeCount(rangeCount)
                                .ranges(ranges)
                                .build())
                        .build();

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
