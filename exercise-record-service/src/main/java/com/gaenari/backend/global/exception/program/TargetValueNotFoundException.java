package com.gaenari.backend.global.exception.program;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class TargetValueNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public TargetValueNotFoundException() {this.errorCode = ErrorCode.TARGET_VALUE_NOT_FOUND; }

}
