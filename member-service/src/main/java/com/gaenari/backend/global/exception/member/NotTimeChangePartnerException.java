package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class NotTimeChangePartnerException extends RuntimeException{
    private final ErrorCode errorCode;
    public NotTimeChangePartnerException() {
        this.errorCode = ErrorCode.PARTNERPET_NOT_FOUND;
    }
}
