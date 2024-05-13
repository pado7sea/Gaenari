package com.gaenari.backend.global.handler;

import com.gaenari.backend.global.exception.feign.ConnectFeignFailException;
import com.gaenari.backend.global.exception.reward.RewardNotFoundException;
import com.gaenari.backend.global.format.code.ErrorCode;
import com.gaenari.backend.global.format.response.ApiResponseCustom;
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

    private final ApiResponseCustom response;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handle(Exception e) {
        log.error("Exception = {}", e.getMessage());
        e.printStackTrace();
        return response.error(ErrorCode.GLOBAL_UNEXPECTED_ERROR);
    }

    @ExceptionHandler(ConnectFeignFailException.class)
    protected ResponseEntity<?> handle(ConnectFeignFailException e) {
        log.error("ConnectFeignFailException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(RewardNotFoundException.class)
    protected ResponseEntity<?> handle(RewardNotFoundException e) {
        log.error("RewardNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

}
