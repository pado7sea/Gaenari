package com.gaenari.backend.domain.reward.repository;

import com.gaenari.backend.domain.reward.entity.Reward;
import com.gaenari.backend.domain.reward.repository.custom.RewardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long>, RewardRepositoryCustom {
}
