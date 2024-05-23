package com.gaenari.backend.domain.record.dto.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProgramType {
    D("거리목표"),
    T("시간목표"),
    I("인터벌"),
    DEFAULT("프로그램 없음");

    private final String value;

}
