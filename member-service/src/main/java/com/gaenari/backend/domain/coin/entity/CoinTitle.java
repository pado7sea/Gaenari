package com.gaenari.backend.domain.coin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CoinTitle {
    REWARD("보상"),
    PETCARE("반려견 케어"),
    PET_PURCHASE("반려견 구매"),
    ITEM_PURCHASE("아이템 구매");

    private final String description;
}
