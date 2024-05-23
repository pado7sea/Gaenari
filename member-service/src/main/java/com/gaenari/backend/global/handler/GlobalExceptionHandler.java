package com.gaenari.backend.global.handler;

import com.gaenari.backend.global.exception.member.*;
import com.gaenari.backend.global.format.response.ApiResponse;
import com.gaenari.backend.global.format.code.ErrorCode;
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

    @ExceptionHandler(AlreadyHavePetException.class)
    protected ResponseEntity<?> handle(AlreadyHavePetException e) {
        log.error("AlreadyHavePetException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(AlreadyMateException.class)
    protected ResponseEntity<?> handle(AlreadyMateException e) {
        log.error("AlreadyMateException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(AlreadyReceivedCallException.class)
    protected ResponseEntity<?> handle(AlreadyReceivedCallException e) {
        log.error("AlreadyReceivedCallException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(AlreadyUnMateException.class)
    protected ResponseEntity<?> handle(AlreadyUnMateException e) {
        log.error("AlreadyUnMateException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(DogNotFoundException.class)
    protected ResponseEntity<?> handle(DogNotFoundException e) {
        log.error("DogNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    protected ResponseEntity<?> handle(DuplicateEmailException e) {
        log.error("DuplicateEmailException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    protected ResponseEntity<?> handle(EmailNotFoundException e) {
        log.error("EmailNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(InvalidLoginAttemptException.class)
    protected ResponseEntity<?> handle(InvalidLoginAttemptException e) {
        log.error("InvalidLoginAttemptException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(LackCoinException.class)
    protected ResponseEntity<?> handle(LackCoinException e) {
        log.error("LackCoinException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(MateNotFoundException.class)
    protected ResponseEntity<?> handle(MateNotFoundException e) {
        log.error("MateNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    protected ResponseEntity<?> handle(MemberNotFoundException e) {
        log.error("MemberNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(NotTimeChangePartnerException.class)
    protected ResponseEntity<?> handle(NotTimeChangePartnerException e) {
        log.error("NotTimeChangePartnerException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(OverTimeAuthCodeException.class)
    protected ResponseEntity<?> handle(OverTimeAuthCodeException e) {
        log.error("OverTimeAuthCodeException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(PartnerPetNotFoundException.class)
    protected ResponseEntity<?> handle(PartnerPetNotFoundException e) {
        log.error("PartnerPetNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ProfileUpdateException.class)
    protected ResponseEntity<?> handle(ProfileUpdateException e) {
        log.error("ProfileUpdateException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(TomeMateException.class)
    protected ResponseEntity<?> handle(TomeMateException e) {
        log.error("TomeMateException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(WaittingMateException.class)
    protected ResponseEntity<?> handle(WaittingMateException e) {
        log.error("WaittingMateException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ConnectFeignFailException.class)
    protected ResponseEntity<?> handle(ConnectFeignFailException e) {
        log.error("ConnectFeignFailException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ExistRewardException.class)
    protected ResponseEntity<?> handle(ExistRewardException e) {
        log.error("ExistRewardException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }


}
