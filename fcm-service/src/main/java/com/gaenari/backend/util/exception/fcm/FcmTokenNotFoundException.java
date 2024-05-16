package com.gaenari.backend.util.exception.fcm;

import com.gaenari.backend.util.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class FcmTokenNotFoundException extends RuntimeException{

  private final ErrorCode errorCode;

  public FcmTokenNotFoundException() {this.errorCode = ErrorCode.FCM_TOKEN_NOT_FOUND;}
}
