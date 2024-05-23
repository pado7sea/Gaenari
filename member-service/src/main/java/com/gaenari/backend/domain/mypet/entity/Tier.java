package com.gaenari.backend.domain.mypet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Tier {
    BRONZE("브론즈"),
    SILVER("실버"),
    GOLD("골드"),
    PLATINUM("플레티넘"),
    DIAMOND("다이아"),
    MASTER("마스터");

    private final String description;
}
