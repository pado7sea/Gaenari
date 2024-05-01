package com.gaenari.backend.domain.record.dto.enumType;

import lombok.Getter;

@Getter
public enum ExerciseType {
    W("걷기"),
    R("달리기"),
    P("운동 프로그램");

    private final String value;

    ExerciseType(String value) {
        this.value = value;
    }

    public static ExerciseType fromValue(String value) {
        for (ExerciseType type : ExerciseType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("알 수 없는 운동 타입 : " + value);
    }

}
