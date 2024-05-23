package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class MateNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public MateNotFoundException() {
        this.errorCode = ErrorCode.NOT_FOUND_MATE;
    }
}
