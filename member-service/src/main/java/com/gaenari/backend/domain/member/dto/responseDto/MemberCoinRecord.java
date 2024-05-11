package com.gaenari.backend.domain.member.dto.responseDto;

import com.gaenari.backend.domain.coin.entity.CoinTitle;
import com.gaenari.backend.domain.member.dto.requestDto.MyPetDto;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoinRecord {
    private int day;
    private Boolean isIncreased;
    private CoinTitle coinTitle;
    private int coinAmount;
    private int balance;
}
