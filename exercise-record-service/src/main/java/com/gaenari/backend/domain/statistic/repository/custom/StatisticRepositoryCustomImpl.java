package com.gaenari.backend.domain.statistic.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatisticRepositoryCustomImpl implements StatisticRepositoryCustom {

    private final JPAQueryFactory query;
}
