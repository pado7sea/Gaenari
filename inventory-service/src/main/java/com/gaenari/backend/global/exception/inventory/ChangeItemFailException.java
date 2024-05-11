package com.gaenari.backend.global.exception.inventory;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class ChangeItemFailException extends RuntimeException{
    private final ErrorCode errorCode;
    public ChangeItemFailException() {
        this.errorCode = ErrorCode.CHANGE_ITEM_FAIL;
    }
}
