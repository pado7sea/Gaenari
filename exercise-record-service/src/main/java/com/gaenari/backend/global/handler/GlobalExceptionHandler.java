package com.gaenari.backend.global.handler;

import com.gaenari.backend.global.exception.exercise.ExerciseTypeMismatchException;
import com.gaenari.backend.global.exception.feign.ConnectFeignFailException;
import com.gaenari.backend.global.exception.program.*;
import com.gaenari.backend.global.exception.record.RecordAccessException;
import com.gaenari.backend.global.exception.record.RecordNotFoundException;
import com.gaenari.backend.global.exception.statistic.StatisticNotFoundException;
import com.gaenari.backend.global.format.code.ErrorCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ApiResponse response;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handle(Exception e) {
        log.error("Exception = {}", e.getMessage());
        e.printStackTrace();
        return response.error(ErrorCode.GLOBAL_UNEXPECTED_ERROR);
    }

    @ExceptionHandler(ExerciseTypeMismatchException.class)
    protected ResponseEntity<?> handle(ExerciseTypeMismatchException e) {
        log.error("ExerciseTypeMismatchException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ConnectFeignFailException.class)
    protected ResponseEntity<?> handle(ConnectFeignFailException e) {
        log.error("ConnectFeignFailException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(IntervalInfoNotFoundException.class)
    protected ResponseEntity<?> handle(IntervalInfoNotFoundException e) {
        log.error("IntervalInfoNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ProgramAccessException.class)
    protected ResponseEntity<?> handle(ProgramAccessException e) {
        log.error("ProgramAccessException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ProgramCreateException.class)
    protected ResponseEntity<?> handle(ProgramCreateException e) {
        log.error("ProgramCreateException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ProgramDeleteException.class)
    protected ResponseEntity<?> handle(ProgramDeleteException e) {
        log.error("ProgramDeleteException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ProgramNotFoundException.class)
    protected ResponseEntity<?> handle(ProgramNotFoundException e) {
        log.error("ProgramNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ProgramUpdateException.class)
    protected ResponseEntity<?> handle(ProgramUpdateException e) {
        log.error("ProgramUpdateException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(TargetValueNotFoundException.class)
    protected ResponseEntity<?> handle(TargetValueNotFoundException e) {
        log.error("TargetValueNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(RecordAccessException.class)
    protected ResponseEntity<?> handle(RecordAccessException e) {
        log.error("RecordAccessException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<?> handle(RecordNotFoundException e) {
        log.error("RecordNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(StatisticNotFoundException.class)
    protected ResponseEntity<?> handle(StatisticNotFoundException e) {
        log.error("StatisticNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

}
