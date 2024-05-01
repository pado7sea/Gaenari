package com.gaenari.backend.domain.record.dto.enumType;

import lombok.Getter;

@Getter
public enum ChallengeType {
    D("거리"), T("시간");

    private final String value;

    ChallengeType(String value) {
        this.value = value;
    }

    public static ChallengeType fromValue(String value) {
        for (ChallengeType type : ChallengeType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("알 수 없는 도전과제 타입 : " + value);
    }
}
