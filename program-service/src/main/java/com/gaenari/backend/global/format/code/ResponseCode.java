package com.gaenari.backend.global.format.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    /* 회원(Member) */
    MEMBER_SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 정상적으로 완료되었습니다."),
    NICKNAME_CHECK_SUCCESS(HttpStatus.OK, "닉네임 검사가 성공적으로 이루어졌습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인이 성공적으로 이루어졌습니다."),
    SOCIAL_LOGIN_SUCCESS(HttpStatus.OK, "소셜 로그인이 성공적으로 이루어졌습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃이 성공적으로 이루어졌습니다."),
    PASSWORD_RESET_SUCCESS(HttpStatus.OK, "비밀번호 재설정이 성공적으로 이루어졌습니다."),
    PASSWORD_UPDATE_SUCCESS(HttpStatus.OK, "비밀번호 업데이트가 성공적으로 이루어졌습니다."),
    MEMBER_INFO_UPDATE_SUCCESS(HttpStatus.OK, "회원 정보가 성공적으로 업데이트되었습니다."),
    FOLLOWINGS_FETCH_SUCCESS(HttpStatus.OK, "팔로잉 리스트를 성공적으로 불러왔습니다."),
    FOLLOWERS_FETCH_SUCCESS(HttpStatus.OK, "팔로워 리스트를 성공적으로 불러왔습니다."),
    FOLLOW_SUCCESS(HttpStatus.OK, "팔로우가 성공적으로 이루어졌습니다."),
    UNFOLLOW_SUCCESS(HttpStatus.OK, "언팔로우가 성공적으로 이루어졌습니다."),
    FOLLOWER_DELETE_SUCCESS(HttpStatus.OK, "팔로워가 성공적으로 삭제되었습니다."),
    FOLLOW_CANCEL_SUCCESS(HttpStatus.OK, "팔로우를 성공적으로 취소했습니다."),
    ACCOUNT_SECESSION_SUCCESS(HttpStatus.OK, "계정 탈퇴가 성공적으로 이루어졌습니다."),
    PRIVACY_SETTING_UPDATED(HttpStatus.OK, "계정 공개여부가 성공적으로 설정되었습니다."),
    NOTIFICATION_SETTING_UPDATED(HttpStatus.OK, "알림 설정이 성공적으로 업데이트되었습니다."),

    EMAIL_VERIFICATION_SENT(HttpStatus.OK, "이메일 인증코드가 성공적으로 발송되었습니다."),
    EMAIL_VERIFIED_SUCCESS(HttpStatus.OK, "이메일이 성공적으로 인증되었습니다."),
    EMAIL_VERIFICATION_REQUEST_SUCCESS(HttpStatus.OK, "이메일 인증요청이 성공적으로 처리되었습니다."),
    NICKNAME_AVAILABLE(HttpStatus.OK, "사용 가능한 닉네임입니다"),
    DUPLICATE_NICKNAME(HttpStatus.OK, "중복된 닉네임입니다"),

    /* 운동 프로그램(Program) */
    PROGRAM_LIST_FETCHED(HttpStatus.OK, "운동 프로그램 목록이 성공적으로 조회되었습니다."),
    PROGRAM_CREATED(HttpStatus.CREATED, "운동 프로그램이 성공적으로 생성되었습니다."),
    PROGRAM_INFO_FETCHED(HttpStatus.OK, "운동 프로그램이 성공적으로 조회되었습니다."),
    PROGRAM_UPDATED(HttpStatus.OK, "운동 프로그램이 성공적으로 업데이트되었습니다."),
    PROGRAM_DELETED(HttpStatus.OK, "운동 프로그램이 성공적으로 삭제되었습니다."),
    FAVORITE_PROGRAM_LIST_FETCHED(HttpStatus.OK, "즐겨찾기 목록이 성공적으로 조회되었습니다."),
    FAVORITE_PROGRAM_UPDATED(HttpStatus.OK, "즐겨찾기가 성공적으로 등록되었습니다."),
    FAVORITE_PROGRAM_DELETED(HttpStatus.OK, "즐겨찾기가 성공적으로 해제되었습니다."),

    FALLBACK_SUCCESS(HttpStatus.OK, "서킷 브레이커가 활성화되어 폴백 로직이 실행됩니다.");

    private final HttpStatus status;
    private final String message;

}

