package com.gaenari.backend.global.format.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 글로벌 예외 처리
    GLOBAL_UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "처리 중 예기치 않은 서버 오류가 발생했습니다."),

    // 프로그램 예외 처리
    PROGRAM_CREATE_FAILED(HttpStatus.BAD_REQUEST, "운동 프로그램을 생성할 수 없습니다."),
    PROGRAM_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "운동 프로그램을 수정할 수 없습니다."),
    PROGRAM_NOT_FOUND(HttpStatus.NOT_FOUND, "운동 프로그램을 찾을 수 없습니다."),
    PROGRAM_DELETE_FAILED(HttpStatus.BAD_REQUEST, "운동 프로그램을 삭제할 수 없습니다."),
    PROGRAM_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "운동 프로그램에 접근할 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
