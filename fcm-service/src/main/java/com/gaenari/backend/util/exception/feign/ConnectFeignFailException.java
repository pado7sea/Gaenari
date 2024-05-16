package com.gaenari.backend.util.exception.feign;

import com.gaenari.backend.util.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class ConnectFeignFailException extends RuntimeException {
    private final ErrorCode errorCode;

    public ConnectFeignFailException() {
        this.errorCode = ErrorCode.CONNECT_FEIGN_FAIL;
    }
}
