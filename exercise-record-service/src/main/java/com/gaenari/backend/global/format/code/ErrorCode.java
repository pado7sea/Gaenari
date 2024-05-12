package com.gaenari.backend.global.format.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 글로벌 예외 처리
    GLOBAL_UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "처리 중 예기치 않은 서버 오류가 발생했습니다."),

    // 요청 관련 예외 처리
    REQUEST_METHOD_NOT_SUPPORTED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다."),
    MEDIA_TYPE_NOT_SUPPORTED(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 미디어 타입입니다."),
    MISSING_SERVLET_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
    SERVLET_REQUEST_BINDING_EXCEPTION(HttpStatus.BAD_REQUEST, "요청 바인딩 오류가 발생했습니다."),
    CONVERSION_NOT_SUPPORTED(HttpStatus.INTERNAL_SERVER_ERROR, "지원되지 않는 변환 유형입니다."),
    TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "타입 불일치 오류가 발생했습니다."),
    MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "메시지를 읽을 수 없습니다."),
    MESSAGE_NOT_WRITABLE(HttpStatus.INTERNAL_SERVER_ERROR, "메시지를 쓸 수 없습니다."),
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "메서드 인자가 유효하지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    MISSING_PATH_VARIABLE(HttpStatus.BAD_REQUEST, "PathVariable 파라미터가 요청에 포함되지 않았습니다."),
    EMPTY_MEMBER(HttpStatus.NOT_FOUND, "회원 정보가 비어있습니다."),
    CONNECT_FEIGN_FAIL(HttpStatus.BAD_REQUEST, "Feign Client 연결에 실패했습니다."),

    ACCESS_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "액세스 토큰을 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "새로운 로그인이 필요합니다. 재로그인을 진행해주세요."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "로그인 정보 형식이 올바르지 않습니다."),
    MODIFIED_TOKEN_DETECTED(HttpStatus.UNAUTHORIZED, "로그인 정보가 변경되었습니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "토큰 형식이 유효하지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "서비스 이용을 위해 로그인이 필요합니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),

    // 프로그램 예외 처리
    PROGRAM_CREATE_FAILED(HttpStatus.BAD_REQUEST, "운동 프로그램을 생성할 수 없습니다."),
    PROGRAM_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "운동 프로그램을 수정할 수 없습니다."),
    PROGRAM_NOT_FOUND(HttpStatus.NOT_FOUND, "운동 프로그램을 찾을 수 없습니다."),
    PROGRAM_DELETE_FAILED(HttpStatus.BAD_REQUEST, "운동 프로그램을 삭제할 수 없습니다."),
    PROGRAM_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "운동 프로그램에 접근할 수 없습니다."),
    EXERCISE_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "운동 프로그램 타입이 아닙니다."),

    TARGET_VALUE_NOT_FOUND(HttpStatus.BAD_REQUEST, "TARGETVALUE가 존재하지 않습니다."),
    INTERVAL_INFO_NOT_FOUND(HttpStatus.BAD_REQUEST, "INTERVAL INFO가 존재하지 않습니다."),

    // 기록 예외 처리
    RECORD_SAVE_FAILED(HttpStatus.BAD_REQUEST, "운동 기록을 저장할 수 없습니다."),
    RECORD_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "기록을 수정할 수 없습니다."),
    RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "기록을 찾을 수 없습니다."),
    RECORD_DELETE_FAILED(HttpStatus.BAD_REQUEST, "기록을 삭제할 수 없습니다."),
    RECORD_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "기록에 접근할 수 없습니다."),

    // 통계 예외 처리
    STATISTIC_CREATE_FAILED(HttpStatus.BAD_REQUEST, "통계를 생성할 수 없습니다."),
    STATISTIC_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "통계를 수정할 수 없습니다."),
    STATISTIC_NOT_FOUND(HttpStatus.NOT_FOUND, "통계를 찾을 수 없습니다."),
    STATISTIC_DELETE_FAILED(HttpStatus.BAD_REQUEST, "통계를 삭제할 수 없습니다."),
    STATISTIC_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "통계에 접근할 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
