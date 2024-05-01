package com.gaenari.backend.domain.record.dto.enumType;

import lombok.Getter;

@Getter
public enum ProgramType {
    D("거리목표"),
    T("시간목표"),
    I("인터벌");

    private final String value;

    ProgramType(String value) {
        this.value = value;
    }

    public static ProgramType fromValue(String value) {
        for (ProgramType type : ProgramType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("알 수 없는 프로그램 타입 : " + value);
    }

}
