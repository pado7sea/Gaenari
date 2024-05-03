package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyReceivedCallException extends RuntimeException{
    private final ErrorCode errorCode;
    public AlreadyReceivedCallException() {
        this.errorCode = ErrorCode.ALREADY_RECEIVED_CALL;
    }
}
