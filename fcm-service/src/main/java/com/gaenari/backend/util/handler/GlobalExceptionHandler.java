package com.gaenari.backend.util.handler;

import com.gaenari.backend.util.exception.fcm.AlreadyRegisterTokenException;
import com.gaenari.backend.util.exception.fcm.FcmTokenNotFoundException;
import com.gaenari.backend.util.exception.feign.ConnectFeignFailException;
import com.gaenari.backend.util.format.code.ErrorCode;
import com.gaenari.backend.util.format.response.ApiResponseCustom;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

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

  @ExceptionHandler(AlreadyRegisterTokenException.class)
  protected ResponseEntity<?> handle(AlreadyRegisterTokenException e) {
    log.error("AlreadyRegisterTokenException = {}", e.getErrorCode().getMessage());
    return response.error(e.getErrorCode());
  }

  @ExceptionHandler(FcmTokenNotFoundException.class)
  protected ResponseEntity<?> handle(FcmTokenNotFoundException e) {
    log.error("FcmTokenNotFoundException = {}", e.getErrorCode().getMessage());
    return response.error(e.getErrorCode());
  }

  @ExceptionHandler(FirebaseMessagingException.class)
  protected ResponseEntity<?> handle(FirebaseMessagingException e) {
    log.error("FirebaseMessagingException = {}", e);
    return response.error(String.valueOf(e.getErrorCode()));
  }

}
