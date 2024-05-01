package com.gaenari.backend.domain.mate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    FRIEND("친구"),
    NOTFRIEND("친구 아님"),
    WAITTING("친구신청 대기 중");

    private final String description;
}
