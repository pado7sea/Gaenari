package com.gaenari.backend.domain.coin.repository;

import com.gaenari.backend.domain.coin.entity.Coin;
import com.gaenari.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {
    @Query("SELECT c FROM Coin c WHERE c.member = :member AND c.year = :year AND c.month = :month ORDER BY c.day DESC, c.time DESC")
    List<Coin> findByMemberAndYearAndMonthOrderByDayAndTimeDesc(@Param("member") Member member, @Param("year") int year, @Param("month") int month);

}
