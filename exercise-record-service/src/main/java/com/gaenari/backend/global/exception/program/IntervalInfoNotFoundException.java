package com.gaenari.backend.global.exception.program;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class IntervalInfoNotFoundException extends RuntimeException{

    private final ErrorCode errorCode;

    public IntervalInfoNotFoundException() {this.errorCode = ErrorCode.INTERVAL_INFO_NOT_FOUND; }

}
