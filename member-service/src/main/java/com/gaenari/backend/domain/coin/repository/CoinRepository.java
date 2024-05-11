package com.gaenari.backend.domain.coin.repository;

import com.gaenari.backend.domain.coin.entity.Coin;
import com.gaenari.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {
    List<Coin> findByMemberAndYearAndMonthOrderByTimeDesc(Member member, int year, int month);
}
