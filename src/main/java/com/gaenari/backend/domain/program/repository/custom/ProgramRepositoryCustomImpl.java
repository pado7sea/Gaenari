package com.gaenari.backend.domain.program.repository.custom;

import com.gaenari.backend.domain.program.dto.responseDto.IntervalInfo;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramListDto;
import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.domain.program.entity.QProgram;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProgramRepositoryCustomImpl implements ProgramRepositoryCustom {

    private final JPAQueryFactory query;

    // 프로그램 완주 횟수 카운트
    @Override
    public Optional<Integer> countFinish(Long programId) {
        // TODO: 레코드랑 연결해서 보여줘야할 거 같은데
        return Optional.empty();
    }

    // 특정 회원이 등록한 프로그램 리스트 조회
    @Override
    public Optional<List<ProgramListDto.ProgramDto>> getProgramList(Long memberId) {
        QProgram qProgram = QProgram.program;
        List<Program> programs = query.selectFrom(qProgram)
                .where(qProgram.memberId.eq(memberId))
                .fetch();

        List<ProgramListDto.ProgramDto> programDtos = programs.stream()
                .map(this::toProgramDto)
                .collect(Collectors.toList());

        return Optional.of(programDtos);
    }

    // 프로그램 엔티티를 받아서 해당 프로그램의 정보를 DTO로 매핑. 프로그램이 인터벌 프로그램인지 여부에 따라 다른 DTO를 생성
    private ProgramListDto.ProgramDto toProgramDto(Program program) {
        ProgramListDto.ProgramDto.ProgramInfo programInfo = null;

        // Type에 따라서 ProgramInfo 인스턴스 생성
        switch (program.getType()) {
            case I:
                // 인터벌 프로그램 정보 생성
                List<IntervalInfo.IntervalRange> ranges = program.getRanges().stream()
                        .map(range -> new IntervalInfo.IntervalRange(
                                range.getId(),
                                range.isRunning(),
                                range.getTime(),
                                range.getSpeed()
                        ))
                        .collect(Collectors.toList());

                if (ranges.isEmpty()) {
                    // 범위 정보가 없는 경우, 이를 적절히 처리
                }

                programInfo = new ProgramListDto.ProgramDto.IntervalProgramInfo(
                        program.getDuration(),
                        program.getSetCount(),
                        ranges
                );
                break;
            case D:
                // 거리 목표 프로그램 정보 생성
                programInfo = new ProgramListDto.ProgramDto.DistanceTargetProgramInfo(program.getTargetValue());
                break;
            case T:
                // 시간 목표 프로그램 정보 생성
                programInfo = new ProgramListDto.ProgramDto.TimeTargetProgramInfo(program.getTargetValue());
                break;
        }

        // ProgramDto 객체 생성
        return new ProgramListDto.ProgramDto(
                program.getId(),
                program.getTitle(),
                program.isFavorite(),
                program.getUsageCount(),
                program.getType(),
                programInfo
        );
    }
    // 특정 프로그램 상세정보 조회
    @Override
    public Optional<ProgramDetailDto> getProgramDetail(Long programId) {
        QProgram qProgram = QProgram.program;
        ProgramDetailDto programDetail = query.select(Projections.constructor(ProgramDetailDto.class,
                        qProgram.id,
                        qProgram.title,
                        qProgram.type,
                        qProgram.targetValue,
                        qProgram.setCount,
                        qProgram.duration,
                        qProgram.isFavorite,
                        qProgram.usageCount
                ))
                .from(qProgram)
                .where(qProgram.id.eq(programId))
                .fetchOne();
        return Optional.ofNullable(programDetail);
    }

//    // 가장 인기 있는 프로그램 리스트 조회
//    @Override
//    public Optional<List<ProgramListDto>> getPopularPrograms(int limit) {
//        QProgram qProgram = QProgram.program;
//        List<ProgramListDto> programs = query.select(new QProgramListDto(
//                        qProgram.id,
//                        qProgram.title,
//                        qProgram.isFavorite,
//                        qProgram.type))
//                .from(qProgram)
//                .orderBy(qProgram.usageCount.desc())  // 사용 횟수 기준으로 내림차순 정렬
//                .limit(limit)
//                .fetch();
//        return Optional.ofNullable(programs);
//    }
//
//    // 프로그램 검색 기능 (키워드, 타입 등에 따라)
//    @Override
//    public Optional<List<ProgramListDto>> searchPrograms(String keyword, ProgramType type) {
//        QProgramEntity qProgram = QProgramEntity.programEntity;
//        BooleanBuilder builder = new BooleanBuilder();
//        if (keyword != null && !keyword.isEmpty()) {
//            builder.and(qProgram.title.containsIgnoreCase(keyword));
//        }
//        if (type != null) {
//            builder.and(qProgram.type.eq(type));
//        }
//        List<ProgramListDto> programs = query.select(new QProgramListDto(
//                        qProgram.id,
//                        qProgram.title,
//                        qProgram.isFavorite,
//                        qProgram.type))
//                .from(qProgram)
//                .where(builder)
//                .fetch();
//        return Optional.ofNullable(programs);
//    }
}
