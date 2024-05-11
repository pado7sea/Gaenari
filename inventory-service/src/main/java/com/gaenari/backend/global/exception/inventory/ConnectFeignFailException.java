package com.gaenari.backend.global.exception.inventory;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class ConnectFeignFailException extends RuntimeException{
    private final ErrorCode errorCode;
    public ConnectFeignFailException() {
        this.errorCode = ErrorCode.CONNECT_FEIGN_FAIL;
    }
}
