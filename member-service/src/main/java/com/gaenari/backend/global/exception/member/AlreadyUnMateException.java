package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyUnMateException extends RuntimeException{
    private final ErrorCode errorCode;
    public AlreadyUnMateException() {
        this.errorCode = ErrorCode.ALREADY_UNMATE;
    }
}
