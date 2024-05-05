package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyHavePetException extends RuntimeException{
    private final ErrorCode errorCode;
    public AlreadyHavePetException() {
        this.errorCode = ErrorCode.ALREADY_HAVE_PET_TYPE;
    }
}
