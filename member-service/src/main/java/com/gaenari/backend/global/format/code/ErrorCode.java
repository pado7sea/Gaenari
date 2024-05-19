package com.gaenari.backend.global.format.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.service.annotation.GetExchange;

@Getter
@RequiredArgsConstructor // final로 선언된 필드 생성자
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
    CONNECT_FEIGN_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "Feign Client 연결에 실패했습니다."),

    ACCESS_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "액세스 토큰을 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "새로운 로그인이 필요합니다. 재로그인을 진행해주세요."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "로그인 정보 형식이 올바르지 않습니다."),
    MODIFIED_TOKEN_DETECTED(HttpStatus.UNAUTHORIZED, "로그인 정보가 변경되었습니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "토큰 형식이 유효하지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "서비스 이용을 위해 로그인이 필요합니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),

    // 회원 관련 예외 처리
    SIGNUP_FAILED(HttpStatus.BAD_REQUEST, "회원 가입에 실패했습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "아이디 혹은 패스워드 정보가 정확하지 않습니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디입니다."),
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST, "유효하지 않은 닉네임입니다."),
    AUTH_CODE_INVALID(HttpStatus.BAD_REQUEST, "인증 코드가 유효하지 않습니다."),
    OVER_TIME_AUTH_CODE(HttpStatus.BAD_REQUEST, "인증 가능시간을 초과하였습니다."),
    LACK_COIN(HttpStatus.CONFLICT, "소지하고 있는 코인이 부족합니다."),

    // 메이트 관련 예외 처리
    PROFILE_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "회원정보를 수정할 수 없습니다."),
    WAITTING_MATE(HttpStatus.CONFLICT, "현재 친구 신청대기 상태입니다."),
    ALREADY_MATE(HttpStatus.CONFLICT, "현재 친구 상태입니다."),
    ALREADY_UNMATE(HttpStatus.CONFLICT, "현재 친구 상태가 아닙니다."),
    TOME_MATE(HttpStatus.CONFLICT, "본인에게 친구 요청할 수 없습니다."),
    ALREADY_RECEIVED_CALL(HttpStatus.CONFLICT, "상대방이 이미 친구 요청을 보낸 상태이기에, 자동으로 친구 요청을 수락함으로써 친구 관계가 맺어졌습니다."),
    NOT_FOUND_MATE(HttpStatus.NOT_FOUND, "해당하는 메이트 내역이 존재하지 않습니다."),

    // 반려견 관련 예외 처리
    PARTNERPET_NOT_FOUND(HttpStatus.BAD_REQUEST, "파트너 반려견이 존재하지 않습니다."),
    ALREADY_HAVE_PET_TYPE(HttpStatus.BAD_REQUEST, "이미 보유중인 종류입니다."),
    NOT_TIME_CHANGE_PARTNER(HttpStatus.BAD_REQUEST, "파트너 반려견을 변경할 수 있는 시간이 아닙니다."),
    DOG_NOT_FOUND(HttpStatus.BAD_REQUEST, "유효하지 않은 개 종류입니다."),
    NOT_REQUEST_AFFECTION(HttpStatus.BAD_REQUEST, "요청할 수 없는 애정도입니다."),

    // 보상
    EXIST_REWARD(HttpStatus.BAD_REQUEST, "아직 수령하지 않은 보상이 존재합니다.");


    private final HttpStatus status;
    private final String message;
}
