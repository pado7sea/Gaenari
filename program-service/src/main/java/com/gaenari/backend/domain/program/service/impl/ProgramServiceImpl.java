package com.gaenari.backend.domain.program.service.impl;

import com.gaenari.backend.domain.client.RecordServiceClient;
import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.requestDto.RangeRequestDto;
import com.gaenari.backend.domain.program.dto.responseDto.*;
import com.gaenari.backend.domain.program.entity.IntervalRange;
import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.domain.program.repository.ProgramRepository;
import com.gaenari.backend.domain.program.service.ProgramBaseService;
import com.gaenari.backend.domain.program.service.ProgramService;
import com.gaenari.backend.global.exception.program.ProgramDeleteException;
import com.gaenari.backend.global.exception.program.ProgramNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class ProgramServiceImpl extends ProgramBaseService implements ProgramService {

    public ProgramServiceImpl(ProgramRepository programRepository, RecordServiceClient recordServiceClient) {
        super(programRepository, recordServiceClient);
    }

    // 프로그램 생성
    @Override
    public Long createProgram(String memberId, ProgramCreateDto programDto) {
        ProgramType type = programDto.getProgramType();
        List<IntervalRange> ranges = new ArrayList<>();

        if (programDto.getInterval() != null) {
            for (RangeRequestDto rangeDto : programDto.getInterval().getRanges()) {
                IntervalRange range = IntervalRange.builder()
                        .isRunning(rangeDto.getIsRunning())
                        .time(rangeDto.getTime())
                        .speed(rangeDto.getSpeed())
                        .program(null) // ProgramEntity 객체 생성 전 일시적으로 null로 설정
                        .build();
                ranges.add(range);
            }
        }

        // ProgramCreateDto -> Program Entity 변환
        Program program = Program.builder()
                .memberId(memberId)
                .title(programDto.getProgramTitle())
                .type(type)
                .targetValue(programDto.getProgramTargetValue())
                .setCount(programDto.getInterval() != null ? programDto.getInterval().getSetCount() : 0)
                .duration(programDto.getInterval() != null ? programDto.getInterval().getDuration() : 0)
                .isFavorite(false) // 기본값
                .usageCount(0)     // 기본값
                .ranges(ranges)
                .build();

        for (IntervalRange range : ranges) {
            range.updateProgram(program); // 프로그램 생성 후 역참조 설정
        }

        // Entity 저장
        Program savedProgram = programRepository.save(program);

        // Id 반환
        return savedProgram.getId();
    }

    // 프로그램 삭제
    @Override
    public void deleteProgram(String memberId, Long programId) {
        Program program = programRepository.findById(programId).orElseThrow(ProgramNotFoundException::new);

        // 프로그램 생성자 ID와 요청한 사용자 ID를 확인
        if (!program.getMemberId().equals(memberId)) {
            throw new ProgramDeleteException();
        }

        programRepository.deleteById(programId);
    }

    // 프로그램 목록 조회
    @Override
    public List<ProgramDto> getProgramList(String memberId) {

        return programRepository.findByMemberIdOrderByIsFavoriteDescUsageCountDesc(memberId).stream()
                .map(program -> {
                    ProgramTypeInfoDto programTypeInfoDto = convertToProgramTypeInfoDto(program);

                    // 마이크로 서비스간 통신을 통해 운동 기록 정보 가져오기
                    List<ProgramDetailDto.UsageLogDto> usageLogDtos = fetchUsageLog(program.getId());

                    int finishedCount = (int) usageLogDtos.stream()
                            .filter(ProgramDetailDto.UsageLogDto::getIsFinished)
                            .count();

                    return ProgramDto.builder()
                            .programId(program.getId())
                            .programTitle(program.getTitle())
                            .isFavorite(program.getIsFavorite())
                            .usageCount(usageLogDtos.size())    // 운동 프로그램 총 사용 횟수
                            .finishedCount(finishedCount)       // 운동 프로그램 완주 횟수
                            .type(program.getType())
                            .program(programTypeInfoDto)
                            .build();
                })
                .toList();
    }

    // 프로그램 상세 정보 조회
    @Override
    public ProgramDetailDto getProgramDetail(String memberId, Long programId) {
        Program program = programRepository.findByMemberIdAndId(memberId, programId);

        if (program == null) {
            throw new ProgramNotFoundException();
        }

        // ProgramTypeInfoDto, IntervalDto, RangeDto는 programType에 따라 설정
        ProgramTypeInfoDto programTypeInfoDto = ProgramTypeInfoDto.builder()
                .targetValue(determineTargetValue(program))
                .intervalInfo(constructIntervalDto(program))
                .build();

        // 마이크로 서비스간 통신을 통해 운동 기록 정보 가져오기
        List<ProgramDetailDto.UsageLogDto> usageLogDtos = fetchUsageLog(program.getId());

        // 운동 프로그램 총 사용 통계
        DoubleSummaryStatistics summary = usageLogDtos.stream()
                .mapToDouble(ProgramDetailDto.UsageLogDto::getDistance)
                .summaryStatistics();

        // 운동 프로그램 완주 횟수
        ProgramDetailDto.TotalRecordDto totalRecordDto = ProgramDetailDto.TotalRecordDto.builder()
                .distance(summary.getSum())
                .time(summary.getSum())
                .cal(summary.getSum())
                .build();

        int finishedCount = (int) usageLogDtos.stream()
                .filter(ProgramDetailDto.UsageLogDto::getIsFinished)
                .count();

        return ProgramDetailDto.builder()
                .programId(program.getId())
                .programTitle(program.getTitle())
                .isFavorite(program.getIsFavorite())
                .type(program.getType())
                .program(programTypeInfoDto)
                .totalRecord(totalRecordDto)            // 운동 프로그램 총 사용 통계
                .usageCount(usageLogDtos.size())        // 운동 프로그램 총 사용 횟수
                .finishedCount(finishedCount)           // 운동 프로그램 완주 횟수
                .usageLog(usageLogDtos)                 // 운동 프로그램 사용 기록
                .build();
    }

    // 타겟 값 설정
    private Double determineTargetValue(Program program) {
        switch (program.getType()) {
            case D, T:
                return program.getTargetValue();
            default:
                return null; // I 타입은 IntervalDto에서 처리
        }
    }

    private IntervalDto constructIntervalDto(Program program) {
        if (program.getType() != ProgramType.I || program.getRanges() == null) {
            return null;
        }

        List<RangeDto> ranges = program.getRanges().stream()
                .map(range -> RangeDto.builder()
                        .id(range.getId())
                        .isRunning(range.getIsRunning())
                        .time(range.getTime())
                        .speed(range.getSpeed())
                        .build())
                .toList();

        double totalDuration = ranges.stream().mapToDouble(RangeDto::getTime).sum();

        return IntervalDto.builder()
                .duration(totalDuration)
                .setCount(program.getSetCount())
                .rangeCount(program.getRanges().size())
                .ranges(ranges)
                .build();
    }


}
