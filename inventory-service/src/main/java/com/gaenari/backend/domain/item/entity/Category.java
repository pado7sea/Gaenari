package com.gaenari.backend.domain.item.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    Wall("벽지"),
    Floor("바닥"),
    Rug("러그"),
    Bowl("밥그릇"),
    Cushion("방석"),
    Toy("장난감");

    private final String description;
}
