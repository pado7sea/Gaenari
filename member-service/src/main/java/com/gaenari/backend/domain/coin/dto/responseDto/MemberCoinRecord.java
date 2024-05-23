package com.gaenari.backend.domain.coin.dto.responseDto;

import com.gaenari.backend.domain.coin.entity.CoinTitle;
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
