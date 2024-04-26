package com.gaenari.backend.global.exception.program;

import com.gaenari.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class ProgramCreateException extends RuntimeException {

    private final ErrorCode errorCode;

    public ProgramCreateException() {this.errorCode = ErrorCode.PROGRAM_CREATE_FAILED; }

}
