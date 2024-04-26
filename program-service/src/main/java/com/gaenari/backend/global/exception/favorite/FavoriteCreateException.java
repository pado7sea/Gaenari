package com.gaenari.backend.global.exception.favorite;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class FavoriteCreateException extends RuntimeException {

    private final ErrorCode errorCode;

    public FavoriteCreateException() {this.errorCode = ErrorCode.PROGRAM_CREATE_FAILED; }

}
