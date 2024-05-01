package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class WaittingMateException extends RuntimeException{

    private final ErrorCode errorCode;

    public WaittingMateException() {
        this.errorCode = ErrorCode.WAITTING_MATE;
    }
}
