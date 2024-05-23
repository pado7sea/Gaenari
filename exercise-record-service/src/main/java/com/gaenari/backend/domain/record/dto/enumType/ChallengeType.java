package com.gaenari.backend.domain.record.dto.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChallengeType {
    D("거리"), T("시간");

    private final String value;

}
