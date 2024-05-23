package com.gaenari.backend.domain.program.service.impl;

import com.gaenari.backend.domain.client.RecordServiceClient;
import com.gaenari.backend.domain.program.dto.responseDto.FavoriteDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramTypeInfoDto;
import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.domain.program.repository.ProgramRepository;
import com.gaenari.backend.domain.program.service.FavoriteService;
import com.gaenari.backend.domain.program.service.ProgramBaseService;
import com.gaenari.backend.global.exception.program.ProgramNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl extends ProgramBaseService implements FavoriteService {

    public FavoriteServiceImpl(ProgramRepository programRepository, RecordServiceClient recordServiceClient) {
        super(programRepository, recordServiceClient);
    }

    // 즐겨찾기 목록 조회
    @Override
    public List<FavoriteDto> getFavoriteList(String accountId) {

        return programRepository.findByAccountIdAndIsFavoriteOrderByUsageCountDesc(accountId, true).stream()
                .map(program -> {
                    ProgramTypeInfoDto programTypeInfoDto = convertToProgramTypeInfoDto(program);

                    // 마이크로 서비스간 통신을 통해 운동 기록 정보 가져오기
                    List<ProgramDetailDto.UsageLogDto> usageLogDtos = fetchUsageLog(program.getId());

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

    @Override
    public Boolean updateFavoriteStatus(String accountId, Long programId) {
        Program program = programRepository.findByAccountIdAndId(accountId, programId);

        if (program == null) {
            throw new ProgramNotFoundException();
        }

        if (program.getIsFavorite()) {
            program.updateIsFavorite(false);
            programRepository.save(program);

            return false;
        } else {
            program.updateIsFavorite(true);
            programRepository.save(program);

            return true;
        }

    }
}
