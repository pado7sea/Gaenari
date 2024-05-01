package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class DogNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public DogNotFoundException() {
        this.errorCode = ErrorCode.DOG_NOT_FOUND;
    }
}
