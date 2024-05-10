package com.gaenari.backend.domain.reward.service;

import com.gaenari.backend.domain.memberChallenge.entity.MemberChallenge;
import com.gaenari.backend.domain.reward.dto.RewardDto;

import java.util.List;

public interface RewardService {
    // 회원 ID로 보상을 받지 않은 도전과제가 있는지 찾기
    boolean findObtainableChallenge(String memberId);
    
    // 회원 ID로 도전과제 ID 리스트 찾기
    List<Integer> getChallengeIdsByMemberId(String memberId);

    // 운동 기록 ID로 도전과제 ID 리스트 찾기
    List<Integer> getChallengeIdsByRecordId(String memberId, Long recordId);

    // 도전 과제 아이디로 받을 수 있는 보상 찾기
    RewardDto getAttainableRewardAndReset(String memberId, Integer challengeId);

    // 도전 과제 아이디로 받을 수 있는 보상 찾기
    RewardDto getAttainableRewardAndUpdate(String memberId, Integer challengeId);

    // 마이크로 서비스 간 통신을 통해서 코인 및 애정도 보상 받게 하기
    void callFeignUpdateCoin(String memberId, Integer coin);

    void callFeignUpdateHeart(String memberId, Integer heart);

    // 도전과제 아이디에 해당하는 멤버챌린지 조회해서 받을 수 있는 보상 개수 리셋하기
    void resetObtainableCount(MemberChallenge memberChallenge);

    // 도전과제 아이디에 해당하는 멤버챌린지 조회해서 받을 수 있는 보상 개수 업데이트하기
    void updateObtainableCount(MemberChallenge memberChallenge, int decreaseValue);
}
