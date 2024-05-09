package com.gaenari.backend.domain.favorite.service.impl;

import com.gaenari.backend.domain.client.RecordServiceClient;
import com.gaenari.backend.domain.favorite.dto.responseDto.FavoriteDto;
import com.gaenari.backend.domain.favorite.repository.FavoriteRepository;
import com.gaenari.backend.domain.favorite.service.FavoriteService;
import com.gaenari.backend.domain.program.dto.responseDto.*;
import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.global.exception.favorite.FavoriteCreateException;
import com.gaenari.backend.global.exception.favorite.FavoriteDeleteException;
import com.gaenari.backend.global.exception.program.ProgramAccessException;
import com.gaenari.backend.global.exception.program.ProgramNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final RecordServiceClient recordServiceClient;

    // 즐겨찾기 목록 조회
    @Override
    public List<FavoriteDto> getFavoriteList(String memberId) {

        return favoriteRepository.findByMemberIdAndIsFavoriteOrderByUsageCountDesc(memberId, true).stream()
                .map(program -> {
                    ProgramTypeInfoDto programTypeInfoDto = convertToProgramTypeInfoDto(program);

                    // 마이크로 서비스간 통신을 통해 운동 기록 정보 가져오기
                    List<ProgramDetailDto.UsageLogDto> usageLogDtos = recordServiceClient.getUsageLog(program.getId());

                    int finishedCount = (int) usageLogDtos.stream()
                            .filter(ProgramDetailDto.UsageLogDto::getIsFinished)
                            .count();

                    return FavoriteDto.builder()
                            .programId(program.getId())
                            .programTitle(program.getTitle())
                            .usageCount(usageLogDtos.size())    // 운동 프로그램 총 사용 횟수
                            .finishedCount(finishedCount)       // 운동 프로그램 완주 횟수
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
    public Boolean updaterFavoriteStatus(String memberId, Long programId) {
        Optional<Program> program = favoriteRepository.findById(programId);

        if (program == null) {
            throw new ProgramNotFoundException();
        }
        if (!Objects.equals(program.get().getMemberId(), memberId)) {
            throw new ProgramAccessException();
        }

        // 프로그램 생성자 ID와 요청한 사용자 ID를 확인
        if (!program.get().getMemberId().equals(memberId)) {
            throw new ProgramAccessException();
        }

        if (program.get().getIsFavorite()) {
            program.get().updateIsFavorite(false);
            favoriteRepository.save(program.get());

            return false;
        } else {
            program.get().updateIsFavorite(true);
            favoriteRepository.save(program.get());

            return true;
        }

    }
}
