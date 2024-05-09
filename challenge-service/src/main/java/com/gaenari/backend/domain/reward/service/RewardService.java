package com.gaenari.backend.domain.reward.service;

import com.gaenari.backend.domain.reward.dto.RewardDto;

public interface RewardService {

    // 도전 과제 아이디로 받을 수 있는 보상 찾기
    RewardDto getAttainableReward(String memberId, Integer challengeId);

    // 마이크로 서비스 간 통신을 통해서 코인 및 애정도 보상 받게 하기
    void callFeignUpdateCoin(String memberId,Integer coin);

    void callFeignUpdateHeart(String memberId,Integer heart);

}
