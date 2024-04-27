package com.gaenari.backend.global.exception.program;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class ProgramUpdateException extends RuntimeException {

    private final ErrorCode errorCode;

    public ProgramUpdateException() {this.errorCode = ErrorCode.PROGRAM_UPDATE_FAILED; }

}
