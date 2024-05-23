package com.gaenari.backend.global.format.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    /* 폴백 */
    FALLBACK_SUCCESS(HttpStatus.OK, "서킷 브레이커가 활성화되어 폴백 로직이 실행됩니다."),

    /* 운동 기록 저장 */
    EXERCISE_RECORD_SAVE_SUCCESS(HttpStatus.CREATED, "운동 기록이 정상적으로 저장되었습니다."),

    /* 기록 */
    RECORD_ALL_FETCHED(HttpStatus.OK, "전체 기록이 성공적으로 조회되었습니다."),
    RECORD_PROGRAM_FETCHED(HttpStatus.OK, "프로그램의 운동 기록이 성공적으로 조회되었습니다."),
    RECORD_MONTH_FETCHED(HttpStatus.OK, "월간 기록이 성공적으로 조회되었습니다."),
    RECORD_WEEK_FETCHED(HttpStatus.OK, "주간 기록이 성공적으로 조회되었습니다."),
    RECORD_DAY_FETCHED(HttpStatus.OK, "일일 기록이 성공적으로 조회되었습니다."),
    RECORD_DETAIL_FETCHED(HttpStatus.OK, "기록 상세가 성공적으로 조회되었습니다."),
    RECORD_CHALLENGE_FETCHED(HttpStatus.OK, "운동 기록의 도전과제 ID 리스트가 성공적으로 조회되었습니다."),
    RECORD_OBTAIN_UPDATED(HttpStatus.OK, "운동 기록의 보상 수령이 성공적으로 완료되었습니다."),

    /* 통계 */
    STATISTIC_ALL_FETCHED(HttpStatus.OK, "전체 통계가 성공적으로 조회되었습니다."),
    STATISTIC_MONTH_FETCHED(HttpStatus.OK, "월간 통계가 성공적으로 조회되었습니다."),
    STATISTIC_WEEK_FETCHED(HttpStatus.OK, "주간 통계가 성공적으로 조회되었습니다.");

    private final HttpStatus status;
    private final String message;

}

