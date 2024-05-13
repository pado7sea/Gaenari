package com.gaenari.backend.domain.record.dto.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExerciseType {
    W("걷기"),
    R("달리기"),
    P("운동 프로그램");

    private final String value;

}
