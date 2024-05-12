package com.gaenari.backend.domain.coin.service;

import com.gaenari.backend.domain.coin.dto.requestDto.MemberCoin;
import com.gaenari.backend.domain.coin.dto.responseDto.MemberCoinHistory;

public interface CoinService {
    int getCoin(String memberEmail); // 보유코인조회
    MemberCoinHistory getCoinRecord(String memberEmail, int year, int month); // 회원 코인내역조회
    void updateCoin(MemberCoin memberCoin); // 코인 증가/감소
}
