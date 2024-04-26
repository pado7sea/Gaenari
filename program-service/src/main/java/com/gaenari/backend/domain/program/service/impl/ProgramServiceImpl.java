package com.gaenari.backend.domain.program.service.impl;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.responseDto.IntervalInfo;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramListDto;
import com.gaenari.backend.domain.program.entity.IntervalRange;
import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.domain.program.repository.ProgramRepository;
import com.gaenari.backend.domain.program.service.ProgramService;
import com.gaenari.backend.global.exception.program.ProgramNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    //    private final MemberRepository memberRepository;  // TODO:이메일로 멤버를 찾기 위해 필요

    public Long createProgram(ProgramCreateDto programDto) {
        // TODO:여기는 member랑 연결한 다음에 처리
//        Member member = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new);

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
//                .memberId(memberId)
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

//    public Long updateProgram(ProgramUpdateDto programDto) {
////        Member member = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new);
//        ProgramEntity programEntity = programRepository.findById(programDto.getProgramId()).orElseThrow(ProgramNotFoundException::new);
//        // TODO: 프로그램의 소유자아이디랑 회원 아이디 일치하는지도 판단해서 예외처리 할 것
////        if (!programEntity.getMember().getId().equals(member.getId())) {
////            throw new ProgramUpdateException();
////        }
//
//        // DTO 데이터로 Entity 필드 업데이트
//        programEntity.setTitle(programDto.getProgramTitle());
//        programEntity.setType(ProgramType.valueOf(programDto.getProgramType())); // 문자열을 Enum으로 변환
//        programEntity.setTargetValue(programDto.getProgramTargetValue());
//        programEntity.setSetCount(programDto.getInterval().getSetCount());
//        programEntity.setDuration(programDto.getInterval().getDuration());
//        // 범위 정보 업데이트
//        programEntity.getRangeList().clear(); // 기존 범위 정보를 클리어
//        if (programDto.getInterval().getRanges() != null) {
//            for (ProgramUpdateDto.RangeDto rangeDto : programDto.getInterval().getRanges()) {
//                RangeEntity range = RangeEntity.builder()
//                        .isRunning(rangeDto.isRunning())
//                        .time(rangeDto.getTime())
//                        .speed(rangeDto.getSpeed())
//                        .program(programEntity)
//                        .build();
//                programEntity.getRangeList().add(range);
//            }
//        }
//
//        // Entity 저장
//        ProgramEntity updatedProgramEntity = programRepository.save(programEntity);
//
//        // Id 반환
//        return updatedProgramEntity.getId();
//    }

    public void deleteProgram(Long programId) {
        //        Member member = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new);
        Program program = programRepository.findById(programId).orElseThrow(ProgramNotFoundException::new);

        // TODO: 프로그램 생성자 ID와 요청한 사용자 ID를 확인
//        if (!programEntity.getMember().getId().equals(member.getId())) {
//            throw new ProgramDeleteException();
//        }

        programRepository.deleteById(programId);
    }

    @Override
    public Optional<List<ProgramListDto>> getProgramList() {
        List<Program> programEntities = programRepository.findAll(); // 모든 프로그램 조회
        if (programEntities.isEmpty()) {
            return Optional.empty(); // 목록이 비어있다면 빈 Optional 반환
        }

        // 개별 ProgramEntity를 ProgramDto로 변환 후, 이를 ProgramListDto로 감싸서 반환
        List<ProgramListDto.ProgramDto> programDtos = programEntities.stream()
                .map(this::convertToProgramDto) // ProgramEntity를 ProgramDto로 변환하는 메서드 호출
                .collect(Collectors.toList());

        // 모든 ProgramDto를 담은 하나의 ProgramListDto 생성
        ProgramListDto programListDto = new ProgramListDto(programDtos);

        // 리스트에 ProgramListDto를 담아서 반환
        return Optional.of(Collections.singletonList(programListDto));
    }

    private ProgramListDto.ProgramDto convertToProgramDto(Program program) {
        ProgramListDto.ProgramDto.ProgramInfo programInfo = convertToProgramInfo(program);

        return new ProgramListDto.ProgramDto(
                program.getId(),
                program.getTitle(),
                program.isFavorite(),
                program.getUsageCount(),
                program.getType(),
                programInfo
        );
    }

    private ProgramListDto.ProgramDto.ProgramInfo convertToProgramInfo(Program program) {
        switch (program.getType()) {
            case D:  // 거리 목표 프로그램
                return new ProgramListDto.ProgramDto.DistanceTargetProgramInfo(program.getTargetValue());

            case T:  // 시간 목표 프로그램
                return new ProgramListDto.ProgramDto.TimeTargetProgramInfo(program.getTargetValue());

            case I:  // 인터벌 프로그램
                List<IntervalInfo.IntervalRange> ranges = program.getRanges().stream()
                        .map(range -> new IntervalInfo.IntervalRange(
                                range.getId(), range.isRunning(), range.getTime(), range.getSpeed()))
                        .collect(Collectors.toList());
                return new  ProgramListDto.ProgramDto.IntervalProgramInfo(
                        program.getDuration(), program.getSetCount(), ranges);

            default:
                throw new IllegalStateException("Unexpected value: " + program.getType());
        }
    }


//    public Optional<List<ProgramListDto>> getProgramList(Long memberId) {
//        return programRepository.getProgramList(memberId);
//    }

    public Optional<ProgramDetailDto> getProgramDetail(Long programId) {
        Optional<Program> programOptional = programRepository.findById(programId);
        if (!programOptional.isPresent()) {
            return Optional.empty();
        }

        Program program = programOptional.get();

        ProgramDetailDto programDetailDto = convertToProgramDetailDto(program);

        // TotalRecordDto, UsageLogDto 등 추가 정보가 필요하면 여기서 조회 및 설정

        // programDto 반환
        return Optional.of(programDetailDto);
    }

    private ProgramDetailDto convertToProgramDetailDto(Program program) {
        ProgramDetailDto.ProgramInfo programInfo = convertToProgramDetailInfo(program);
//        ProgramDetailDto.UsageLogDto usageLog = convertToProgramDetailInfo(program);


        return new ProgramDetailDto(
                program.getId(),
                program.getTitle(),
                program.isFavorite(),
                program.getType(),
                programInfo
//                program.getUsageCount(),
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
                return new  ProgramDetailDto.IntervalProgramInfo(
                        program.getDuration(), program.getSetCount(), ranges);

            default:
                throw new IllegalStateException("Unexpected value: " + program.getType());
        }
    }

//    public Optional<List<ProgramListDto>> getPopularPrograms(int limit) {
//        return programRepository.getPopularPrograms(limit);
//    }
//
//    public Optional<List<ProgramListDto>> searchPrograms(String keyword, ProgramType type) {
//        return programRepository.searchPrograms(keyword, type);
//    }
}
