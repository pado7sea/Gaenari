package com.gaenari.backend.domain.reward.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RewardRepositoryCustomImpl implements RewardRepositoryCustom {

    private final JPAQueryFactory query;
}
