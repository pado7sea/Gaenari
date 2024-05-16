package com.gaenari.backend.domain.statistic.repository;

import com.gaenari.backend.domain.statistic.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    /**
     * 회원 ID에 해당하는 통계를 찾습니다.
     *
     * @param accountId 회원 ID
     * @return 해당 회원의 통계
     */
    Statistic findByAccountId(String accountId);

    /**
     * 특정 날짜 이전에 생성된 통계들을 조회합니다.
     *
     * @param oneWeekAgo 특정 날짜 (일주일 이전)
     * @return 특정 날짜 이전에 생성된 통계 리스트
     */
    List<Statistic> findByDateBefore(LocalDateTime oneWeekAgo);
}
