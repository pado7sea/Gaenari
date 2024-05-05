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
    PROGRAM_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "운동 프로그램에 접근할 수 없습니다."),

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
    STATISTIC_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "통계에 접근할 수 없습니다."),

    // 도전과제 예외 처리
    CHALLENGE_CREATE_FAILED(HttpStatus.CREATED, "도전과제가 이미 존재합니다. 도전과제를 생성할 수 없습니다."),
    CHALLENGE_NOT_FOUND(HttpStatus.OK, "도전과제를 찾을 수 없습니다."),

    ACHIEVED_TROPHY_NOT_FOUND(HttpStatus.OK, "달성 업적을 찾을 수 없습니다."),
    ACHIEVED_MISSION_NOT_FOUND(HttpStatus.OK, "달성 미션을 찾을 수 없습니다."),
    ACHIEVED_TROPHY_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "달성 업적에 접근할 수 없습니다."),
    ACHIEVED_MISSION_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "달성 미션에 접근할 수 없습니다.");


    private final HttpStatus status;
    private final String message;
}
