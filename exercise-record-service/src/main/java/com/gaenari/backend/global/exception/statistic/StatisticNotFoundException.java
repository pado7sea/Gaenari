package com.gaenari.backend.global.exception.statistic;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class StatisticNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public StatisticNotFoundException() {this.errorCode = ErrorCode.STATISTIC_NOT_FOUND; }

}
