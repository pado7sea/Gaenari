package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyMateException extends RuntimeException{
    private final ErrorCode errorCode;
    public AlreadyMateException() {
        this.errorCode = ErrorCode.ALREADY_MATE;
    }
}
