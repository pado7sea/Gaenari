package com.gaenari.backend.util.exception.fcm;

import com.gaenari.backend.util.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyRegisterTokenException extends RuntimeException{
  private final ErrorCode errorCode;

  public AlreadyRegisterTokenException() {this.errorCode = ErrorCode.ALREADY_REGISTER_TOKEN;}
}
