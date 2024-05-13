package com.gaenari.backend.global.format.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor // final로 선언된 필드 생성자
public enum ResponseCode {

    // 회원(Member)
    MEMBER_SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 정상적으로 완료되었습니다."),
    NICKNAME_CHECK_SUCCESS(HttpStatus.OK, "닉네임 검사가 성공적으로 이루어졌습니다."),
    ALREADY_USE_NICKNAME(HttpStatus.OK, "이미 사용중인 닉네임입니다."),
    AVAILABLE_NICKNAME(HttpStatus.OK, "사용가능한 닉네임입니다."),
    ALREADY_USE_EMAIL(HttpStatus.OK, "이미 사용중인 이메일입니다."),
    AVAILABLE_EMAIL(HttpStatus.OK, "사용가능한 이메일입니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인이 성공적으로 이루어졌습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃이 성공적으로 이루어졌습니다."),
    PASSWORD_RESET_SUCCESS(HttpStatus.OK, "비밀번호 재설정이 성공적으로 이루어졌습니다."),
    PASSWORD_UPDATE_SUCCESS(HttpStatus.OK, "비밀번호 업데이트가 성공적으로 이루어졌습니다."),
    MEMBER_NICKNAME_UPDATE_SUCCESS(HttpStatus.OK, "회원 닉네임이 성공적으로 업데이트되었습니다."),
    MEMBER_PWD_UPDATE_SUCCESS(HttpStatus.OK, "회원 비밀번호가 성공적으로 업데이트되었습니다."),
    MEMBER_INFO_UPDATE_SUCCESS(HttpStatus.OK, "회원 정보가 성공적으로 업데이트되었습니다."),
    MATES_SUCCESS(HttpStatus.OK, "친구 목록을 성공적으로 불러왔습니다."),
    MATE_SENT_SUCCESS(HttpStatus.OK, "친구신청 발신 리스트를 성공적으로 불러왔습니다."),
    MATE_RECEIVED_SUCCESS(HttpStatus.OK, "친구신청 수신 리스트를 성공적으로 불러왔습니다."),
    APPLY_MATE_SUCCESS(HttpStatus.OK, "친구신청이 성공적으로 이루어졌습니다."),
    ACCEPT_MATE_SUCCESS(HttpStatus.OK, "친구수락이 성공적으로 이루어졌습니다."),
    REJECT_MATE_SUCCESS(HttpStatus.OK, "친구거부가 성공적으로 이루어졌습니다."),
    REMOVE_MATE_SUCCESS(HttpStatus.OK, "친구삭제가 성공적으로 이루어졌습니다."),
    SEARCH_MEMBER_SUCCESS(HttpStatus.OK, "멤버 검색이 성공적으로 이루어졌습니다."),
    COIN_FETCH_SUCCESS(HttpStatus.OK, "보유코인개수를 성공적으로 불러왔습니다."),
    ISSUED_WATCH_AUTH_CODE_SUCCESS(HttpStatus.OK, "워치 연동인증번호가 성공적으로 발급되었습니다."),
    WATCH_CONNECT_SUCCESS(HttpStatus.OK, "워치 연동이 정상적으로 이루어졌습니다."),
    ACCOUNT_SECESSION_SUCCESS(HttpStatus.OK, "계정 탈퇴가 성공적으로 이루어졌습니다."),
    NOTIFICATION_SETTING_UPDATED(HttpStatus.OK, "알림 설정이 성공적으로 업데이트되었습니다."),

    // 반려견
    ADOPT_NEW_PET_SUCCESS(HttpStatus.OK, "새로운 반려견이 성공적으로 입양되었습니다."),
    PARTNER_PET_CHANGE_SUCCESS(HttpStatus.OK, "파트너 반려견이 성공적으로 변경되었습니다."),
    PARTNER_PET_GET_SUCCESS(HttpStatus.OK, "파트너 반려견을 성공적으로 불러왔습니다."),
    PARTNER_PET_AFFECTION_INCREASE_SUCCESS(HttpStatus.OK, "파트너 반려견의 애정도가 성공적으로 증가하였습니다."),

    // 인벤토리
    EQUIP_ITEMS_SUCCESS(HttpStatus.OK, "기본 아이템들을 성공적으로 적용하였습니다."),
    DELETE_MEMBER_ITEMS_SUCCESS(HttpStatus.OK, "가지고 있는 아이템들을 성공적으로 삭제하였습니다."),
    GET_MY_ITEMS_SUCCESS(HttpStatus.OK, "나의 보관함의 아이템들을 성공적으로 조회하였습니다."),
    GET_MY_PETS_SUCCESS(HttpStatus.OK, "나의 보관함의 강아지들을 성공적으로 조회하였습니다."),
    GET_EQUIP_ITEMS_SUCCESS(HttpStatus.OK, "적용된 나의 아이템들을 성공적으로 조회하였습니다."),
    UPDATE_EQUIP_ITEMS_SUCCESS(HttpStatus.OK, "아이템 적용이 성공적으로 이루어졌습니다."),
    PURCHASE_ITEMS_SUCCESS(HttpStatus.OK, "아이템 구매가 성공적으로 이루어졌습니다."),
    VISIT_FRIEND_HOME_SUCCESS(HttpStatus.OK, "친구집을 성공적으로 방문하였습니다.");

    private final HttpStatus status;
    private final String message;

}
