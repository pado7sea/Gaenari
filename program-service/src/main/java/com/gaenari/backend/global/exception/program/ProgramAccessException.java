package com.gaenari.backend.global.exception.program;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class ProgramAccessException extends RuntimeException {

    private final ErrorCode errorCode;

    public ProgramAccessException() {this.errorCode = ErrorCode.PROGRAM_ACCESS_DENIED; }

}
