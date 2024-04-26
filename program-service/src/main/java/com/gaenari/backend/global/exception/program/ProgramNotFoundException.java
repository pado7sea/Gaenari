package com.gaenari.backend.global.exception.program;

import com.gaenari.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class ProgramNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ProgramNotFoundException() {this.errorCode = ErrorCode.PROGRAM_NOT_FOUND; }

}
