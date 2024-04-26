package com.gaenari.backend.domain.favorite.repository.custom;

import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.domain.program.entity.QProgram;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FavoriteRepositoryCustomImpl implements FavoriteRepositoryCustom {

    private final JPAQueryFactory query;

    // 특정 회원이 등록한 즐겨찾기 프로그램 리스트 조회
    @Override
    public List<Program> getFavoriteList(Long memberId) {
        QProgram qProgram = QProgram.program;

        return query.selectFrom(qProgram)
                .where(qProgram.memberId.eq(memberId)
                        .and(qProgram.isFavorite.isTrue()))
                .orderBy(qProgram.usageCount.desc())  // 사용 횟수 기준으로 내림차순 정렬
                .fetch();
    }

}
