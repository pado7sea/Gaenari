package com.gaenari.backend.global.exception.member;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class ProfileUpdateException extends RuntimeException {

    private final ErrorCode errorCode;

    public ProfileUpdateException() {
        this.errorCode = ErrorCode.PROFILE_UPDATE_FAILED;
    }
}
