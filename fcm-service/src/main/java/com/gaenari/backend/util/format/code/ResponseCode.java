package com.gaenari.backend.util.format.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    /* 토큰 저장 */
    EXERCISE_RECORD_SAVE_SUCCESS(HttpStatus.CREATED, "디바이스 토큰이 정상적으로 저장되었습니다."),

    /* 알림 전송 */
    STATISTIC_WEEK_FETCHED(HttpStatus.OK, "푸시 알림이 성공적으로 전송되었습니다.");

    private final HttpStatus status;
    private final String message;

}

