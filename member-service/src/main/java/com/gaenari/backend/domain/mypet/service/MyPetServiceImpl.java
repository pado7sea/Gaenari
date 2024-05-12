package com.gaenari.backend.domain.mypet.service;

import com.gaenari.backend.domain.client.challenge.ChallengeServiceClient;
import com.gaenari.backend.domain.member.entity.Member;
import com.gaenari.backend.domain.member.repository.MemberRepository;
import com.gaenari.backend.domain.mypet.dto.requestDto.Adopt;
import com.gaenari.backend.domain.mypet.dto.requestDto.HeartChange;
import com.gaenari.backend.domain.mypet.dto.requestDto.IncreaseAffection;
import com.gaenari.backend.domain.mypet.dto.responseDto.FriendPetDetail;
import com.gaenari.backend.domain.mypet.dto.responseDto.Pets;
import com.gaenari.backend.domain.mypet.entity.Dog;
import com.gaenari.backend.domain.mypet.entity.MyPet;
import com.gaenari.backend.domain.mypet.entity.Tier;
import com.gaenari.backend.domain.mypet.repository.DogRepository;
import com.gaenari.backend.domain.mypet.repository.MyPetRepository;
import com.gaenari.backend.global.exception.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPetServiceImpl implements MyPetService{
    private final MemberRepository memberRepository;
    private final DogRepository dogRepository;
    private final MyPetRepository myPetRepository;
    private final ChallengeServiceClient challengeServiceClient;
    @Override // 반려견 입양
    public void adopt(String memberEmail, Adopt adopt) {
        Member member = memberRepository.findByEmail(memberEmail);
        Dog dog = dogRepository.findById(adopt.getId())
                .orElseThrow(DogNotFoundException::new);
        // 현재 가지고 있는 반려견 종류들과 겹치는지 확인
        List<MyPet> myPetList = myPetRepository.findByMemberId(member.getId());
        for(MyPet myPet : myPetList){
            if(myPet.getDog().getId() == adopt.getId()){
                throw new AlreadyHavePetException();
            }
        }
        // 회원 코인 감소
        int haveCoin = member.getCoin() - dog.getPrice();
        if(haveCoin < 0){
            throw new LackCoinException();
        }
        Member updateCoin = Member.builder()
                .Id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .gender(member.getGender())
                .height(member.getHeight())
                .weight(member.getWeight())
                .coin(haveCoin) // 변경
                .device(member.getDevice())
                .myPetList(member.getMyPetList())
                .build();
        memberRepository.save(updateCoin);
        // 새로운 반려견 등록
        MyPet myPet = MyPet.builder()
                .member(member)
                .dog(dog)
                .name(adopt.getName())
                .isPartner(false)
                .build();
        myPetRepository.save(myPet);
    }

    @Override // 파트너 반려견 변경
    public void changePartner(String memberEmail, Long dogId) {
        Member member = memberRepository.findByEmail(memberEmail);
        // 받지 않은 보상이 있는지 확인
        boolean reward = challengeServiceClient.isGetReward(memberEmail);
        if(reward){
            throw new ExistRewardException();
        }
        // 현재 파트너 반려견 조회
        MyPet currentMyPet = myPetRepository.findByMemberIdAndIsPartner(member.getId(), true)
                .orElseThrow(PartnerPetNotFoundException::new);
        // 변경 가능 시간인지 확인
        LocalDateTime changeTime = currentMyPet.getChangeTime();
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(changeTime, currentTime);
        // 차이가 3분 초과인지 확인
        Boolean timeCheck = duration.getSeconds() < 180; // 3분은 180초
        if(timeCheck){
            throw new NotTimeChangePartnerException();
        }
        // 현재 파트너 반려견 false 처리
        MyPet cancelMyPet = MyPet.builder()
                .Id(currentMyPet.getId())
                .member(currentMyPet.getMember())
                .dog(currentMyPet.getDog())
                .name(currentMyPet.getName())
                .affection(currentMyPet.getAffection())
                .tier(currentMyPet.getTier())
                .isPartner(false) // 변경
                .changeTime(null)
                .build();
        myPetRepository.save(cancelMyPet);
        // 새로운 파트너 반려견 true 처리
        MyPet newMyPet = myPetRepository.findByMemberIdAndDogId(member.getId(), dogId)
                .orElseThrow(PartnerPetNotFoundException::new);
        MyPet changeMyPet = MyPet.builder()
                .Id(newMyPet.getId())
                .member(newMyPet.getMember())
                .dog(newMyPet.getDog())
                .name(newMyPet.getName())
                .isPartner(true) // 변경
                .changeTime(LocalDateTime.now()) // 변경한 시간 저장
                .build();
        myPetRepository.save(changeMyPet);
    }

    @Override // 파트너 반려견 조회
    public FriendPetDetail getPartner(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail);
        // 현재 파트너 반려견 조회
        MyPet currentMyPet = myPetRepository.findByMemberIdAndIsPartner(member.getId(), true)
                .orElseThrow(PartnerPetNotFoundException::new);
        // FriendPetDetail response 객체로 변환
        FriendPetDetail friendPetDetail = FriendPetDetail.builder()
                .id(currentMyPet.getDog().getId())
                .name(currentMyPet.getName())
                .affection(currentMyPet.getAffection())
                .tier(currentMyPet.getTier())
                .changeTime(currentMyPet.getChangeTime())
                .build();
        return friendPetDetail;
    }

    @Override // 반려견 애정도 증가
    public void increaseAffection(String memberEmail, IncreaseAffection affection) {
        Member member = memberRepository.findByEmail(memberEmail);
        // 펫 조회
        MyPet myPet = myPetRepository.findByMemberIdAndDogId(member.getId(), affection.getId())
                .orElseThrow(PartnerPetNotFoundException::new);
        // 현재 펫 티어 조회
        Tier currentTier = myPet.getTier();
        // 애정도 계산
        int increaseHeart = myPet.getAffection() + affection.getAffection();
        // 레벨 업
        if(increaseHeart>=100){
            increaseHeart = 30;
            switch (currentTier) {
                case BRONZE:
                    currentTier = Tier.SILVER;
                    break;
                case SILVER:
                    currentTier = Tier.GOLD;
                    break;
                case GOLD:
                    currentTier = Tier.PLATINUM;
                    break;
                case PLATINUM:
                    currentTier = Tier.DIAMOND;
                    break;
                case DIAMOND:
                    currentTier = Tier.MASTER;
                    break;
                case MASTER:
                    currentTier = Tier.MASTER; // 티어가 마스터인 경우, 일단 그대로
                    break;
            }
        }
        // 애정도 증가
        MyPet updateMyPet = MyPet.builder()
                .Id(myPet.getId())
                .member(myPet.getMember())
                .dog(myPet.getDog())
                .name(myPet.getName())
                .affection(increaseHeart) // 변경
                .tier(currentTier)        // 변경
                .isPartner(myPet.getIsPartner())
                .changeTime(myPet.getChangeTime())
                .build();
        myPetRepository.save(updateMyPet);
    }

    @Override // [Feign] 반려견 애정도 증/감
    public void changeHeart(HeartChange heartChange) {
        // 회원 조회
        Member member = memberRepository.findByEmail(heartChange.getMemberEmail());
        // 현재 파트너 반려견 조회
        MyPet currentMyPet = myPetRepository.findByMemberIdAndIsPartner(member.getId(), true)
                .orElseThrow(PartnerPetNotFoundException::new);
        int changeAffection = 0;
        Tier currentTier = currentMyPet.getTier();
        if(heartChange.getIsIncreased()){
            // true : 증가
            changeAffection = currentMyPet.getAffection() + heartChange.getHeart();
            if(changeAffection>=100){
                changeAffection = 30;
                switch (currentTier) {
                    case BRONZE:
                        currentTier = Tier.SILVER;
                        break;
                    case SILVER:
                        currentTier = Tier.GOLD;
                        break;
                    case GOLD:
                        currentTier = Tier.PLATINUM;
                        break;
                    case PLATINUM:
                        currentTier = Tier.DIAMOND;
                        break;
                    case DIAMOND:
                        currentTier = Tier.MASTER;
                        break;
                    case MASTER:
                        currentTier = Tier.MASTER; // 티어가 마스터인 경우, 일단 그대로
                        break;
                }
            }
        }else{
            // false : 감소
            changeAffection = currentMyPet.getAffection() - heartChange.getHeart();
            if(changeAffection<=0){
                changeAffection = 70;
                switch (currentTier) {
                    case BRONZE:
                        currentTier = Tier.BRONZE; // 티어가 브론즈인 경우, 그대로
                        changeAffection = 0;
                        break;
                    case SILVER:
                        currentTier = Tier.BRONZE;
                        break;
                    case GOLD:
                        currentTier = Tier.SILVER;
                        break;
                    case PLATINUM:
                        currentTier = Tier.GOLD;
                        break;
                    case DIAMOND:
                        currentTier = Tier.PLATINUM;
                        break;
                    case MASTER:
                        currentTier = Tier.DIAMOND;
                        break;
                }
            }
        }
        // 애정도 변화
        MyPet updateMyPet = MyPet.builder()
                .Id(currentMyPet.getId())
                .member(currentMyPet.getMember())
                .dog(currentMyPet.getDog())
                .name(currentMyPet.getName())
                .affection(changeAffection) // 변경
                .tier(currentTier) // 변경
                .isPartner(currentMyPet.getIsPartner())
                .changeTime(currentMyPet.getChangeTime())
                .build();
        myPetRepository.save(updateMyPet);

    }

    @Override
    public List<Pets> getPets(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail);
        List<MyPet> myPetList = myPetRepository.findByMemberId(member.getId());
        List<Pets> petsList = new ArrayList<>();
        for(MyPet myPet : myPetList){
            // 해당 종의 정보 가져오기
            Dog dog = dogRepository.findById(myPet.getDog().getId())
                    .orElseThrow(DogNotFoundException::new);
            Pets pets = Pets.builder()
                    .id(myPet.getDog().getId())
                    .name(myPet.getName())
                    .affection(myPet.getAffection())
                    .tier(myPet.getTier())
                    .isPartner(myPet.getIsPartner())
                    .price(dog.getPrice())
                    .build();
            petsList.add(pets);
        }

        return petsList;
    }

    @Override // 강아지 가격 조회
    public int getDogPrice(int dogId) {
        Dog dog = dogRepository.findById((long)dogId)
                .orElseThrow(DogNotFoundException::new);
        return dog.getPrice();
    }
}
