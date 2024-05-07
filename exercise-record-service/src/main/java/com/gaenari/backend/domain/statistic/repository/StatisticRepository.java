package com.gaenari.backend.domain.statistic.repository;

import com.gaenari.backend.domain.statistic.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    Statistic findByMemberId(String memberId);

}
