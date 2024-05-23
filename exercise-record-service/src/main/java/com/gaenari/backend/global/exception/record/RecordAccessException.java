package com.gaenari.backend.global.exception.record;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class RecordAccessException extends RuntimeException {

    private final ErrorCode errorCode;

    public RecordAccessException() {this.errorCode = ErrorCode.RECORD_ACCESS_DENIED; }
}
