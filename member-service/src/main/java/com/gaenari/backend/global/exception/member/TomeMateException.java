package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class TomeMateException extends RuntimeException{
    private final ErrorCode errorCode;
    public TomeMateException() {
        this.errorCode = ErrorCode.TOME_MATE;
    }
}
