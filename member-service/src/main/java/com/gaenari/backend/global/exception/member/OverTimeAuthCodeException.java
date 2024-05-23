package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class OverTimeAuthCodeException extends RuntimeException {

    private final ErrorCode errorCode;

    public OverTimeAuthCodeException() {
        this.errorCode = ErrorCode.OVER_TIME_AUTH_CODE;
    }
}
