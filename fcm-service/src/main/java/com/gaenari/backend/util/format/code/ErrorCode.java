package com.gaenari.backend.util.format.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 글로벌 예외 처리
    GLOBAL_UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "처리 중 예기치 않은 서버 오류가 발생했습니다."),

    CONNECT_FEIGN_FAIL(HttpStatus.BAD_REQUEST, "Feign Client 연결에 실패했습니다."),
    ALREADY_REGISTER_TOKEN(HttpStatus.BAD_REQUEST, "이미 등록된 디바이스 토큰입니다."),
    FCM_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 토큰이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
