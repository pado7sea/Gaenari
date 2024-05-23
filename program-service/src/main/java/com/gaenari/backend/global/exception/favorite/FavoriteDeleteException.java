package com.gaenari.backend.global.exception.favorite;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class FavoriteDeleteException extends RuntimeException {

    private final ErrorCode errorCode;

    public FavoriteDeleteException() {this.errorCode = ErrorCode.FAVORITE_DELETE_FAILED; }

}
