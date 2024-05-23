package com.gaenari.backend.domain.reward.service;

import com.gaenari.backend.domain.memberChallenge.entity.MemberChallenge;
import com.gaenari.backend.domain.reward.dto.RewardDto;

import java.util.List;

public interface RewardService {

    /**
     * 지정된 회원이 보상을 받지 않은 도전과제가 있는지 확인한다.
     *
     * @param accountId String 형태로, 확인할 회원의 고유 식별자.
     * @return boolean 보상을 받을 수 있는 도전과제가 있는 경우 true, 그렇지 않은 경우 false를 반환한다.
     */
    boolean findObtainableChallenge(String accountId);

    /**
     * 지정된 회원 ID에 연결된 도전과제 ID 목록을 조회한다.
     *
     * @param accountId String 형태로, 조회할 회원의 고유 식별자.
     * @return List<Integer> 회원과 연결된 도전과제 ID 목록을 반환한다.
     */
    List<Integer> getChallengeIdsByAccountId(String accountId);

    /**
     * 지정된 운동 기록 ID에 연결된 도전과제 ID 목록을 조회한다.
     *
     * @param accountId String 형태로, 조회할 회원의 고유 식별자.
     * @param recordId Long 형태로, 조회할 운동 기록의 고유 식별자.
     * @return List<Integer> 해당 운동 기록과 연결된 도전과제 ID 목록을 반환한다.
     */
    List<Integer> getChallengeIdsByRecordId(String accountId, Long recordId);

    /**
     * 지정된 도전과제 ID에 대해 받을 수 있는 보상을 조회하고 보상 개수를 초기화한다.
     *
     * @param accountId String 형태로, 보상을 받을 회원의 고유 식별자.
     * @param challengeId Integer 형태로, 조회할 도전과제의 고유 식별자.
     * @return RewardDto 조회된 보상 정보를 담은 DTO 객체를 반환한다.
     */
    RewardDto getAttainableRewardAndReset(String accountId, Integer challengeId);

    /**
     * 지정된 도전과제 ID에 대해 받을 수 있는 보상을 조회하고 보상 개수를 업데이트한다.
     *
     * @param accountId String 형태로, 보상을 받을 회원의 고유 식별자.
     * @param challengeId Integer 형태로, 조회할 도전과제의 고유 식별자.
     * @return RewardDto 조회된 보상 정보를 담은 DTO 객체를 반환한다.
     */
    RewardDto getAttainableRewardAndUpdate(String accountId, Integer challengeId);

    /**
     * 지정된 도전과제 ID에 대해 받을 수 있는 보상을 조회한다.
     *
     * @param accountId String 형태로, 보상을 받을 회원의 고유 식별자.
     * @param challengeId Integer 형태로, 조회할 도전과제의 고유 식별자.
     * @return RewardDto 조회된 보상 정보를 담은 DTO 객체를 반환한다.
     */
    RewardDto getAttainableReward(String accountId, Integer challengeId);

    /**
     * 마이크로 서비스 간 통신을 통해 지정된 회원의 코인 수를 업데이트한다.
     *
     * @param accountId String 형태로, 코인을 업데이트할 회원의 고유 식별자.
     * @param coin Integer 형태로, 추가할 코인의 수.
     */
    void callFeignUpdateCoin(String accountId, Integer coin);

    /**
     * 마이크로 서비스 간 통신을 통해 지정된 회원의 애정도를 업데이트한다.
     *
     * @param accountId String 형태로, 애정도를 업데이트할 회원의 고유 식별자.
     * @param heart Integer 형태로, 추가할 애정도의 수.
     */
    void callFeignUpdateHeart(String accountId, Integer heart);

    /**
     * 도전과제 ID에 해당하는 멤버챌린지의 받을 수 있는 보상 개수를 리셋한다.
     *
     * @param memberChallenge MemberChallenge 객체, 리셋할 멤버챌린지.
     */
    void resetObtainableCount(MemberChallenge memberChallenge);

    /**
     * 도전과제 ID에 해당하는 멤버챌린지의 받을 수 있는 보상 개수를 업데이트한다.
     *
     * @param memberChallenge MemberChallenge 객체, 업데이트할 멤버챌린지.
     * @param decreaseValue int 형태로, 줄일 보상 개수.
     */
    void updateObtainableCount(MemberChallenge memberChallenge, int decreaseValue);

    /**
     * 마이크로 서비스 간 통신을 통해 지정된 회원의 운동기록을 보상 수령 완료로 업데이트한다.
     *
     * @param accountId String 형태로, 애정도를 업데이트할 회원의 고유 식별자.
     * @param recordId Long 형태로, 운동 기록 ID.
     */
    void updateRecordObtained(String accountId, Long recordId);

    /**
     * 마이크로 서비스 간 통신을 통해 지정된 회원의 모든 운동기록을 보상 수령 완료로 업데이트한다.
     *
     * @param accountId String 형태로, 애정도를 업데이트할 회원의 고유 식별자.
     */
    void updateAllRecordObtained(String accountId);

}

