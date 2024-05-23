package com.gaenari.backend.global.exception.exercise;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class ExerciseTypeMismatchException extends RuntimeException{
    private final ErrorCode errorCode;

    public ExerciseTypeMismatchException() {
        this.errorCode = ErrorCode.EXERCISE_TYPE_MISMATCH;
    }
}
