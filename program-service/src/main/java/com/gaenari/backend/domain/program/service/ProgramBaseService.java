package com.gaenari.backend.domain.program.service;

import com.gaenari.backend.domain.client.RecordServiceClient;
import com.gaenari.backend.domain.program.dto.responseDto.IntervalDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramTypeInfoDto;
import com.gaenari.backend.domain.program.dto.responseDto.RangeDto;
import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.domain.program.repository.ProgramRepository;
import com.gaenari.backend.global.exception.feign.ConnectFeignFailException;
import com.gaenari.backend.global.exception.program.ProgramNotFoundException;
import com.gaenari.backend.global.format.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class ProgramBaseService {

    protected final ProgramRepository programRepository;
    protected final RecordServiceClient recordServiceClient;

    protected ProgramTypeInfoDto convertToProgramTypeInfoDto(Program program) {
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
                throw new ProgramNotFoundException();
        }
    }

    // 마이크로 서비스간 통신을 통해 운동 기록 정보 가져오기
    protected List<ProgramDetailDto.UsageLogDto> fetchUsageLog(Long programId) {
        ResponseEntity<GenericResponse<List<ProgramDetailDto.UsageLogDto>>> response = recordServiceClient.getUsageLog(programId);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ConnectFeignFailException();
        }
        System.out.println(response.getBody());
        return response.getBody().getData();
    }
}
