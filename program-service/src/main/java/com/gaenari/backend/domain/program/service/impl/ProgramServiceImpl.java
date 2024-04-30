package com.gaenari.backend.domain.program.service.impl;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.responseDto.IntervalInfo;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDto;
import com.gaenari.backend.domain.program.entity.IntervalRange;
import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.domain.program.repository.ProgramRepository;
import com.gaenari.backend.domain.program.service.ProgramService;
import com.gaenari.backend.global.exception.program.ProgramDeleteException;
import com.gaenari.backend.global.exception.program.ProgramNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;

    public Long createProgram(Long memberId, ProgramCreateDto programDto) {
        ProgramType type = ProgramType.valueOf(programDto.getProgramType());
        List<IntervalRange> ranges = new ArrayList<>();

        if (programDto.getInterval() != null) {
            for (ProgramCreateDto.RangeDto rangeDto : programDto.getInterval().getRanges()) {
                IntervalRange range = IntervalRange.builder()
                        .isRunning(rangeDto.isRunning())
                        .time(rangeDto.getTime())
                        .speed(rangeDto.getSpeed())
                        .program(null) // ProgramEntity 객체 생성 전 일시적으로 null로 설정
                        .build();
                ranges.add(range);
            }
        }

        // DTO -> Entity 변환
        Program program = Program.builder()
                .memberId(memberId)
                .title(programDto.getProgramTitle())
                .type(type)
                .targetValue(programDto.getProgramTargetValue())
                .setCount(programDto.getInterval() != null ? programDto.getInterval().getSetCount() : 0)
                .duration(programDto.getInterval() != null ? programDto.getInterval().getDuration() : 0)
                .isFavorite(false) // Default
                .usageCount(0)     // Default
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

    public void deleteProgram(Long memberId, Long programId) {
        Program program = programRepository.findById(programId).orElseThrow(ProgramNotFoundException::new);

        // 프로그램 생성자 ID와 요청한 사용자 ID를 확인
        if (!program.getMemberId().equals(memberId)) {
            throw new ProgramDeleteException();
        }

        programRepository.deleteById(programId);
    }

    @Override
    public List<ProgramDto> getProgramList(Long memberId) {
        List<ProgramDto> programDtos = convertToProgramDto(programRepository.getProgramList(memberId));

        // 리스트에 ProgramListDto를 담아서 반환
        return programDtos;
    }

    private List<ProgramDto> convertToProgramDto(List<Program> programs) {
        return programs.stream()
                .map(this::convertToProgramDto)
                .collect(Collectors.toList());
    }

    private ProgramDto convertToProgramDto(Program program) {
        ProgramDto.ProgramInfo programInfo = convertToProgramInfo(program);

        int finishedCount = programRepository.countFinish(program.getId());

        return new ProgramDto(
                program.getId(),
                program.getTitle(),
                program.isFavorite(),
                program.getUsageCount(),
                finishedCount,
                program.getType(),
                programInfo
        );
    }

    private ProgramDto.ProgramInfo convertToProgramInfo(Program program) {
        switch (program.getType()) {
            case D:  // 거리 목표 프로그램
                return new ProgramDto.DistanceTargetProgramInfo(program.getTargetValue());

            case T:  // 시간 목표 프로그램
                return new ProgramDto.TimeTargetProgramInfo(program.getTargetValue());

            case I:  // 인터벌 프로그램
                List<IntervalInfo.IntervalRange> ranges = program.getRanges().stream()
                        .map(range -> new IntervalInfo.IntervalRange(
                                range.getId(), range.isRunning(), range.getTime(), range.getSpeed()))
                        .collect(Collectors.toList());

                int setCount = program.getSetCount();
                int rangeCount = ranges.size();
                int setDuration = ranges.stream().mapToInt(IntervalInfo.IntervalRange::getTime).sum(); // 각 range의 시간의 합

                return new ProgramDto.IntervalProgramInfo(
                        setDuration*setCount, setCount, rangeCount, ranges);

            default:
                throw new IllegalStateException("Unexpected value: " + program.getType());
        }
    }

    public ProgramDetailDto getProgramDetail(Long memberId, Long programId) {
        Optional<Program> program = programRepository.findById(programId);

        if (program.isEmpty()) {
           throw new ProgramDeleteException();
        }

        // 프로그램 생성자 ID와 요청한 사용자 ID를 확인
        if (!program.get().getMemberId().equals(memberId)) {
            throw new ProgramDeleteException();
        }

        ProgramDetailDto programDetailDto = convertToProgramDetailDto(program.get());

        return programDetailDto;
    }

    private ProgramDetailDto convertToProgramDetailDto(Program program) {
        ProgramDetailDto.ProgramInfo programInfo = convertToProgramDetailInfo(program);
//        ProgramDetailDto.UsageLogDto usageLog = convertToProgramDetailInfo(program);


        return new ProgramDetailDto(
                program.getId(),
                program.getTitle(),
                program.isFavorite(),
                program.getType(),
                programInfo,
                program.getUsageCount()
        );
    }

    private ProgramDetailDto.ProgramInfo convertToProgramDetailInfo(Program program) {
        switch (program.getType()) {
            case D:
                return new ProgramDetailDto.DistanceTargetProgramInfo(program.getTargetValue());
            case T:
                return new ProgramDetailDto.TimeTargetProgramInfo(program.getTargetValue());
            case I:
                List<IntervalInfo.IntervalRange> ranges = program.getRanges().stream()
                        .map(range -> new IntervalInfo.IntervalRange(
                                range.getId(), range.isRunning(), range.getTime(), range.getSpeed()))
                        .collect(Collectors.toList());

                int setCount = program.getSetCount();
                int rangeCount = ranges.size();
                int setDuration = ranges.stream().mapToInt(IntervalInfo.IntervalRange::getTime).sum(); // 각 range의 시간의 합

                return new ProgramDetailDto.IntervalProgramInfo(
                        setDuration*setCount, setCount, rangeCount, ranges);
            default:
                throw new IllegalStateException("Unexpected value: " + program.getType());
        }
    }
}
