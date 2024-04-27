package com.gaenari.backend.domain.program.repository.custom;

import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.domain.program.entity.QProgram;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProgramRepositoryCustomImpl implements ProgramRepositoryCustom {

    private final JPAQueryFactory query;

    // 프로그램 완주 횟수 카운트
    @Override
    public Integer countFinish(Long programId) {
        // TODO: 레코드랑 연결해서 보여줘야할 거 같은데
        return 0;
    }

    // 특정 회원이 등록한 프로그램 리스트 조회
    @Override
    public List<Program> getProgramList(Long memberId) {
        QProgram qProgram = QProgram.program;
        List<Program> programs = query.selectFrom(qProgram)
                .where(qProgram.memberId.eq(memberId))
                .orderBy(qProgram.usageCount.desc())  // 사용 횟수 기준으로 내림차순 정렬
                .fetch();

        return programs;
    }

    // 특정 프로그램 상세정보 조회
    @Override
    public Program getProgram(Long programId) {
        QProgram qProgram = QProgram.program;
        Program program = query.selectFrom(qProgram)
                .where(qProgram.id.eq(programId))
                .fetchFirst();

        return program;
    }
}
