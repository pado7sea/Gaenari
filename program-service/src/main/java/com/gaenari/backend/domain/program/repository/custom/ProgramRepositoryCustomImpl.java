package com.gaenari.backend.domain.program.repository.custom;

import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.domain.program.entity.QProgram;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProgramRepositoryCustomImpl implements ProgramRepositoryCustom {

    private final JPAQueryFactory query;

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

}
