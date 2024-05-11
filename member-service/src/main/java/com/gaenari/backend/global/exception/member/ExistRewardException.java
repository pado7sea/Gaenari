package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class ExistRewardException extends RuntimeException {

    private final ErrorCode errorCode;

    public ExistRewardException() {
        this.errorCode = ErrorCode.EXIST_REWARD;
    }
}
