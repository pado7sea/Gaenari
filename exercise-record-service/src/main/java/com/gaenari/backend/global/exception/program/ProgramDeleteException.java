package com.gaenari.backend.global.exception.program;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class ProgramDeleteException extends RuntimeException {

    private final ErrorCode errorCode;

    public ProgramDeleteException() {this.errorCode = ErrorCode.PROGRAM_DELETE_FAILED; }

}
