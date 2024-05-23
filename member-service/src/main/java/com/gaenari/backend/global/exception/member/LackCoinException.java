package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class LackCoinException extends RuntimeException{
    private final ErrorCode errorCode;
    public LackCoinException() {
        super(ErrorCode.LACK_COIN.getMessage());
        this.errorCode = ErrorCode.LACK_COIN;
    }
}
