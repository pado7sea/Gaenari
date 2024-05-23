package com.gaenari.backend.global.exception.record;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class RecordNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public RecordNotFoundException() {this.errorCode = ErrorCode.RECORD_NOT_FOUND; }
}
