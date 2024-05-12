package com.gaenari.backend.domain.coin.service;

import com.gaenari.backend.domain.coin.dto.requestDto.MemberCoin;
import com.gaenari.backend.domain.coin.dto.responseDto.MemberCoinHistory;
import com.gaenari.backend.domain.coin.dto.responseDto.MemberCoinRecord;
import com.gaenari.backend.domain.coin.entity.Coin;
import com.gaenari.backend.domain.coin.repository.CoinRepository;
import com.gaenari.backend.domain.member.entity.Member;
import com.gaenari.backend.domain.member.repository.MemberRepository;
import com.gaenari.backend.global.exception.member.EmailNotFoundException;
import com.gaenari.backend.global.exception.member.LackCoinException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinServiceImpl implements CoinService {
    private final MemberRepository memberRepository;
    private final CoinRepository coinRepository;

    @Override // 보유코인 조회
    public int getCoin(String memberEmail) {
        Member mem = memberRepository.findByEmail(memberEmail);
        int coin = mem.getCoin();
        return coin;
    }

    @Override // 회원 코인내역조회
    public MemberCoinHistory getCoinRecord(String memberEmail, int year, int month) {
        // 회원 조회
        Member member = memberRepository.findByEmail(memberEmail);
        // 코인 내역 조회
        List<Coin> coinList = coinRepository.findByMemberAndYearAndMonthOrderByTimeDesc(member, year, month);
        // MemberCoinRecord Dto로 변환
        List<MemberCoinRecord> memberCoinRecordList = new ArrayList<>();
        for(Coin coin : coinList){
            MemberCoinRecord memberCoinRecord = MemberCoinRecord.builder()
                    .day(coin.getDay())
                    .isIncreased(coin.getIsIncreased())
                    .coinTitle(coin.getCoinTitle())
                    .coinAmount(coin.getCoinAmount())
                    .balance(coin.getBalance())
                    .build();
            memberCoinRecordList.add(memberCoinRecord);
        }
        // 최종 MemberCoinHistory Dto로 변환
        MemberCoinHistory memberCoinHistory = MemberCoinHistory.builder()
                .year(year)
                .month(month)
                .memberCoinRecordList(memberCoinRecordList)
                .build();

        return memberCoinHistory;
    }

    @Override // 코인 증가/감소
    public void updateCoin(MemberCoin memberCoin) {
        // 회원 조회
        Member member = memberRepository.findByEmail(memberCoin.getMemberEmail());
        if(member==null)
            throw new EmailNotFoundException();
        // 변동코인
        int changeCoin = 0;
        if(memberCoin.getIsIncreased()){
            changeCoin = member.getCoin() + memberCoin.getCoinAmount();
        }else{
            changeCoin = member.getCoin() - memberCoin.getCoinAmount();
        }
        if(changeCoin < 0){
            throw new LackCoinException();
        }
        // 코인 증/감
        Member registMember = Member.builder()
                .Id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .gender(member.getGender())
                .height(member.getHeight())
                .weight(member.getWeight())
                .coin(changeCoin) // 변경
                .device(member.getDevice())
                .deviceTime(member.getDeviceTime())
                .myPetList(member.getMyPetList())
                .build();
        memberRepository.save(registMember);

        // 코인 내역 테이블에 변동 정보가 저장되어야함
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now(); // 시,분,초만 저장

        // 현재 날짜의 년, 월, 일을 각각 저장
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();

        Coin coin = Coin.builder()
                .member(member)
                .year(year)
                .month(month)
                .day(day)
                .time(currentTime)
                .isIncreased(memberCoin.getIsIncreased())
                .coinTitle(memberCoin.getCoinTitle())
                .coinAmount(memberCoin.getCoinAmount())
                .balance(changeCoin)
                .build();
        coinRepository.save(coin);
    }
}
