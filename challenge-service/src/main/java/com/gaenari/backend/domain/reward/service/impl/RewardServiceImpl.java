package com.gaenari.backend.domain.reward.service.impl;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.client.member.MemberServiceClient;
import com.gaenari.backend.domain.client.member.dto.CoinTitle;
import com.gaenari.backend.domain.client.member.dto.HeartChangeDto;
import com.gaenari.backend.domain.client.member.dto.MemberCoinDto;
import com.gaenari.backend.domain.client.record.RecordServiceClient;
import com.gaenari.backend.domain.memberChallenge.entity.MemberChallenge;
import com.gaenari.backend.domain.memberChallenge.repository.MemberChallengeRepository;
import com.gaenari.backend.domain.reward.dto.RewardDto;
import com.gaenari.backend.domain.reward.service.RewardService;
import com.gaenari.backend.global.exception.feign.ConnectFeignFailException;
import com.gaenari.backend.global.exception.reward.RewardNotFoundException;
import com.gaenari.backend.global.format.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RecordServiceClient recordServiceClient;
    private final MemberServiceClient memberServiceClient;
    private final MemberChallengeRepository memberChallengeRepository;

    // 회원 ID로 보상을 받지 않은 도전과제가 있는지 찾기
    @Override
    public boolean findObtainableChallenge(String accountId) {
        long count = memberChallengeRepository.findByAccountIdAndObtainableGreaterThan(accountId, 0).size();
        return count != 0;
    }

    // 회원 ID로 도전과제 ID 리스트 찾기
    @Override
    public List<Integer> getChallengeIdsByAccountId(String accountId) {
        // 멤버가 가지고 있는 완료한 기록 조회
        List<MemberChallenge> memberChallenges = memberChallengeRepository.findByAccountIdAndObtainableGreaterThan(accountId, 0);
        // 도전 과제 ID를 저장할 리스트 생성
        List<Integer> challengeIds = new ArrayList<>();

        // 완료된 기록들을 순회하면서 도전 과제 ID를 추출하여 리스트에 저장
        for (MemberChallenge memberChallenge : memberChallenges) {
            // 도전 과제의 ID를 리스트에 추가
            challengeIds.add(memberChallenge.getChallenge().getId());
        }

        return challengeIds;
    }

    // 마이크로 서비스간 통신을 통해 운동 기록 ID로 도전과제 ID 리스트 조회
    @Override
    public List<Integer> getChallengeIdsByRecordId(String accountId, Long recordId) {
        ResponseEntity<GenericResponse<List<Integer>>> response = recordServiceClient.getChallengeIdsByRecordId(accountId, recordId);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ConnectFeignFailException();
        }
        return response.getBody().getData();
    }

    // 도전 과제 아이디로 받을 수 있는 보상 찾기
    @Override
    public RewardDto getAttainableRewardAndReset(String accountId, Integer challengeId) {
        // 도전 과제 아이디로 모든 멤버 챌린지 조회
        MemberChallenge memberChallenge = memberChallengeRepository.findByAccountIdAndChallengeId(accountId, challengeId);

        // 도전 과제 아이디로 조회된 멤버 챌린지가 없거나 obtainableCount 가 0 이하인 경우
        if (memberChallenge == null || memberChallenge.getObtainable() <= 0) {
            throw new RewardNotFoundException();
        }

        // 업적 및 미션 카테고리에 따라 얻을 수 있는 코인 및 애정도 계산
        ChallengeCategory category = memberChallenge.getChallenge().getCategory();

        int attainableCoin = 0;
        int attainableHeart = 0;

        if (category == ChallengeCategory.TROPHY || category == ChallengeCategory.MISSION) {
            attainableCoin = memberChallenge.getObtainable() * memberChallenge.getChallenge().getCoin();
            System.out.println("attainableCoin = " + attainableCoin);
        }
        if (category == ChallengeCategory.MISSION) {
            // 업적일 경우에는 애정도 계산 x
            attainableHeart = memberChallenge.getObtainable() * memberChallenge.getChallenge().getHeart();
            System.out.println("attainableHeart = " + attainableHeart);
        }

        // 받을 수 있는 보상 횟수를 0으로 리셋
        resetObtainableCount(memberChallenge);

        // 코인과 애정도 반환
        return RewardDto.builder()
                .accountId(accountId)
                .coin(attainableCoin)
                .heart(attainableHeart)
                .build();
    }

    @Override
    public RewardDto getAttainableRewardAndUpdate(String accountId, Integer challengeId) {
        // 도전 과제 아이디로 모든 멤버 챌린지 조회
        MemberChallenge memberChallenge = memberChallengeRepository.findByAccountIdAndChallengeId(accountId, challengeId);

        // 도전 과제 아이디로 조회된 멤버 챌린지가 없거나 obtainableCount 가 0 이하인 경우
        if (memberChallenge == null || memberChallenge.getObtainable() <= 0) {
            throw new RewardNotFoundException();
        }

        // 업적 및 미션 카테고리에 따라 얻을 수 있는 코인 및 애정도 계산
        ChallengeCategory category = memberChallenge.getChallenge().getCategory();

        int attainableCoin = 0;
        int attainableHeart = 0;

        if (category == ChallengeCategory.TROPHY || category == ChallengeCategory.MISSION) {
            attainableCoin = memberChallenge.getChallenge().getCoin();
            System.out.println("attainableCoin = " + attainableCoin);
        }
        if (category == ChallengeCategory.MISSION) {
            // 업적일 경우에는 애정도 계산 x
            attainableHeart = memberChallenge.getChallenge().getHeart();
            System.out.println("attainableHeart = " + attainableHeart);
        }

        // 받을 수 있는 보상 횟수를 업데이트
        updateObtainableCount(memberChallenge, 1);

        // 코인과 애정도 반환
        return RewardDto.builder()
                .accountId(accountId)
                .coin(attainableCoin)
                .heart(attainableHeart)
                .build();
    }


    // 마이크로 서비스 간 통신을 통해서 코인 보상 받게 하기
    @Override
    public void callFeignUpdateCoin(String accountId, Integer coin) {
        MemberCoinDto memberCoinDto = MemberCoinDto.builder()
                .accountId(accountId)
                .coinAmount(coin)
                .isIncreased(true)
                .coinTitle(CoinTitle.REWARD)
                .build();

        // 마이크로 서비스 간 통신을 통해 회원의 코인 증가시키기
        ResponseEntity<GenericResponse<?>> response = memberServiceClient.updateCoin(memberCoinDto);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ConnectFeignFailException();
        }
    }

    // 마이크로 서비스 간 통신을 통해서 애정도 보상 받게 하기
    @Override
    public void callFeignUpdateHeart(String accountId, Integer heart) {
        HeartChangeDto heartChangeDto = HeartChangeDto.builder()
                .accountId(accountId)
                .isIncreased(true)
                .heart(heart)
                .build();

        // 마이크로 서비스 간 통신을 통해 반려견의 애정도 증가시키기
        ResponseEntity<GenericResponse<?>> response = memberServiceClient.updateHeart(heartChangeDto);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ConnectFeignFailException();
        }
    }

    // 도전과제 아이디에 해당하는 멤버챌린지 조회해서 받을 수 있는 보상 개수 리셋하기
    @Override
    public void resetObtainableCount(MemberChallenge memberChallenge) {
        memberChallenge.updateObtainable(0);
        memberChallengeRepository.save(memberChallenge);
    }

    // 도전과제 아이디에 해당하는 멤버챌린지 조회해서 받을 수 있는 보상 개수 업데이트하기
    @Override
    public void updateObtainableCount(MemberChallenge memberChallenge, int decreaseValue) {
        ChallengeCategory category = memberChallenge.getChallenge().getCategory();

        if (category == ChallengeCategory.MISSION) {
            // 미션일 경우 1 감소
            memberChallenge.updateObtainable(memberChallenge.getObtainable() - decreaseValue);
        } else if (category == ChallengeCategory.TROPHY) {
            // 업적일 경우 0
            memberChallenge.updateObtainable(0);
        }

        // 0보다 작으면 0으로 변경
        if (memberChallenge.getObtainable() < 0) {
            memberChallenge.updateObtainable(0);
        }

        memberChallengeRepository.save(memberChallenge);
    }


}
