package com.gaenari.backend.global.format.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    /* 운동 기록 저장 */
    EXERCISE_RECORD_SAVE_SUCCESS(HttpStatus.CREATED, "운동 기록이 정상적으로 저장되었습니다."),

    /* 기록(Record) */
    RECORD_ALL_FETCHED(HttpStatus.OK, "전체 기록이 성공적으로 조회되었습니다."),
    RECORD_MONTH_FETCHED(HttpStatus.OK, "월간 기록이 성공적으로 조회되었습니다."),
    RECORD_WEEK_FETCHED(HttpStatus.OK, "주간 기록이 성공적으로 조회되었습니다."),
    RECORD_DAY_FETCHED(HttpStatus.OK, "주간 기록이 성공적으로 조회되었습니다."),
    RECORD_DETAIL_FETCHED(HttpStatus.OK, "기록 상세가 성공적으로 조회되었습니다."),

    /* 통계 */
    STATISTIC_ALL_FETCHED(HttpStatus.OK, "전체 통계가 성공적으로 조회되었습니다."),
    STATISTIC_MONTH_FETCHED(HttpStatus.OK, "월간 통계가 성공적으로 조회되었습니다."),
    STATISTIC_WEEK_FETCHED(HttpStatus.OK, "주간 통계가 성공적으로 조회되었습니다."),

    /* 도전과제 */
    CHALLENGE_CREATED(HttpStatus.CREATED, "도전과제가 정상적으로 생성되었습니다."),
    CHALLENGE_FETCHED(HttpStatus.OK, "도전과제가 성공적으로 조회되었습니다."),
    CHALLENGE_DELETED_ALL(HttpStatus.OK,"도전과제를 모두 삭제하였습니다." ),
    ACHIEVED_TROPHY_FETCHED(HttpStatus.OK, "달성 업적이 성공적으로 조회되었습니다."),
    ACHIEVED_MISSION_FETCHED(HttpStatus.OK, "달성 미션이 성공적으로 조회되었습니다."),

    /* 보상 */
    REWARD_ALL_RECEIVE_SUCCESS(HttpStatus.OK, "모든 보상을 성공적으로 수령하였습니다."),
    REWARD_RECORD_RECEIVE_SUCCESS(HttpStatus.OK, "해당 운동 기록과 관련된 보상을 성공적으로 수령하였습니다."),
    REWARD_ONE_RECEIVE_SUCCESS(HttpStatus.OK, "도전 과제 보상을 성공적으로 수령하였습니다."),
    REWARD_EXIST_SUCCESS(HttpStatus.OK, "받지 않은 보상 여부를 성공적으로 조회하였습니다.");

    private final HttpStatus status;
    private final String message;

    }

