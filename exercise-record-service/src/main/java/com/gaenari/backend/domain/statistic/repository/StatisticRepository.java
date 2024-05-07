package com.gaenari.backend.domain.statistic.repository;

import com.gaenari.backend.domain.statistic.entity.Statistic;
import com.gaenari.backend.domain.statistic.repository.custom.StatisticRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticRepository extends JpaRepository<Statistic, Long>, StatisticRepositoryCustom {


    Optional<Statistic> findByMemberId(Long memberId);
}
