package com.gaenari.backend.domain.reward.service.impl;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.client.member.MemberServiceClient;
import com.gaenari.backend.domain.client.member.dto.HeartChangeDto;
import com.gaenari.backend.domain.client.member.dto.MemberCoinDto;
import com.gaenari.backend.domain.memberChallenge.entity.MemberChallenge;
import com.gaenari.backend.domain.memberChallenge.repository.MemberChallengeRepository;
import com.gaenari.backend.domain.reward.dto.RewardDto;
import com.gaenari.backend.domain.reward.service.RewardService;
import com.gaenari.backend.global.exception.reward.RewardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final MemberServiceClient memberServiceClient;
    private final MemberChallengeRepository memberChallengeRepository;

    // 도전 과제 아이디로 받을 수 있는 보상 찾기
    @Override
    public RewardDto getAttainableReward(String memberId, Integer challengeId) {
        // 멤버챌린지에서 도전 과제 아이디로 조회
        MemberChallenge memberChallenge = memberChallengeRepository.findByMemberIdAndChallengeId(memberId, challengeId);

        // obtainable count 가 0이면 받을 보상이 없음
        if (memberChallenge.getObtainable() <= 0) {
            throw new RewardNotFoundException();
        }



        // 업적 및 미션 카테고리에 따라 얻을 수 있는 코인 및 애정도 계산
        ChallengeCategory category = memberChallenge.getChallenge().getCategory();

        Integer attainableCoin = 0;
        Integer attainableHeart = 0;

        System.out.println("memberChallengeId"+memberChallenge.getId());

        if (category == ChallengeCategory.TROPHY || category == ChallengeCategory.MISSION) {
            attainableCoin = memberChallenge.getObtainable() * memberChallenge.getChallenge().getCoin();
            System.out.println("count -> "+memberChallenge.getObtainable());
            System.out.println("coin -> "+memberChallenge.getChallenge().getCoin());

            System.out.println("attainableCoin = " + attainableCoin);
        }
        if (category == ChallengeCategory.MISSION) {
            // 업적일 경우에는 애정도 계산 x
            attainableHeart = memberChallenge.getObtainable() * memberChallenge.getChallenge().getHeart();
            System.out.println("attainableHeart = " + attainableHeart);
            System.out.println("heart"+memberChallenge.getChallenge().getHeart());
        }

        // 받을 수 있는 보상 횟수를 0으로 리셋
        resetObtainableCount(memberChallenge);

        // 코인과 애정도 반환
        return RewardDto.builder()
                .challengeId(challengeId)
                .coin(attainableCoin)
                .heart(attainableHeart)
                .build();
    }

    // 마이크로 서비스 간 통신을 통해서 코인 보상 받게 하기
    @Override
    public void callFeignUpdateCoin(String memberId, Integer coin) {
        MemberCoinDto memberCoinDto = MemberCoinDto.builder()
                .memberEmail(memberId)
                .coin(coin)
                .build();

        // 마이크로 서비스 간 통신을 통해 회원의 코인 증가시키기
        ResponseEntity<?> response = memberServiceClient.updateCoin(memberCoinDto);
        // TODO : 응답에 따른 예외처리 해주면 좋을 듯
    }

    // 마이크로 서비스 간 통신을 통해서 애정도 보상 받게 하기
    @Override
    public void callFeignUpdateHeart(String memberId, Integer heart) {
        HeartChangeDto heartChangeDto = HeartChangeDto.builder()
                .memberEmail(memberId)
                .isIncreased(true)
                .heart(heart)
                .build();

        // 마이크로 서비스 간 통신을 통해 반려견의 애정도 증가시키기
        ResponseEntity<?> response = memberServiceClient.updateHeart(heartChangeDto);
    }

    // 도전과제 아이디에 해당하는 멤버챌린지 조회해서 받을 수 있는 보상 개수 업데이트하기
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

    // 도전과제 아이디에 해당하는 멤버챌린지 조회해서 받을 수 있는 보상 개수 리셋하기
    public void resetObtainableCount(MemberChallenge memberChallenge) {
        memberChallenge.updateObtainable(0);
        memberChallengeRepository.save(memberChallenge);
    }
}
