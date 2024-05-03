package com.gaenari.backend.domain.mate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Call {
    SENT("발신"),
    RECEIVED("수신"),
    NO("없음");

    private final String description;
}
