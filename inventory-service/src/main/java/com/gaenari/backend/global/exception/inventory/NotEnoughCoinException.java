package com.gaenari.backend.global.exception.inventory;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class NotEnoughCoinException extends RuntimeException{
    private final ErrorCode errorCode;
    public NotEnoughCoinException() {
        this.errorCode = ErrorCode.NOT_ENOUGH_COIN;
    }
}
