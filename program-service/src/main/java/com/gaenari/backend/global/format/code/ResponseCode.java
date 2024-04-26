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
    FAVORITE_PROGRAM_UPDATED(HttpStatus.OK, "즐겨찾기에 성공적으로 등록되었습니다."),
    FAVORITE_PROGRAM_DELETED(HttpStatus.OK, "즐겨찾기에서 성공적으로 삭제되었습니다."),

    /* 책(Book) */
    BOOK_LIST_FETCHED(HttpStatus.OK, "나의 책 목록이 성공적으로 조회되었습니다."),
    BOOK_CREATED(HttpStatus.CREATED, "책이 성공적으로 생성되었습니다."),
    BOOKMARK_CREATED(HttpStatus.CREATED, "북마크가 성공적으로 생성되었습니다."),
    BOOK_INFO_FETCHED(HttpStatus.OK, "책 정보가 성공적으로 조회되었습니다."),
    BOOK_UPDATED(HttpStatus.OK, "책 정보가 성공적으로 업데이트되었습니다."),
    BOOK_DELETED(HttpStatus.OK, "책이 성공적으로 삭제되었습니다."),
    BOOK_SUBSCRIBED(HttpStatus.CREATED, "책 구독이 성공적으로 이루어졌습니다."),
    BOOK_UNSUBSCRIBED(HttpStatus.OK, "책 구독이 성공적으로 취소되었습니다."),
    BOOK_LIST_FROM_PAPER(HttpStatus.OK, "페이퍼가 포함된 책 목록이 성공적으로 조회되었습니다."),

    /* 카테고리(Category) */
    CATEGORIES_FETCHED(HttpStatus.OK, "카테고리 목록이 성공적으로 조회되었습니다."),
    CATEGORY_BOOKS_FETCHED(HttpStatus.OK, "카테고리 내 책 목록이 성공적으로 조회되었습니다."),
    CATEGORY_CREATED(HttpStatus.CREATED, "카테고리가 성공적으로 생성되었습니다."),
    CATEGORY_UPDATED(HttpStatus.OK, "카테고리 정보가 성공적으로 업데이트되었습니다."),
    CATEGORY_DELETED(HttpStatus.OK, "카테고리가 성공적으로 삭제되었습니다."),

    /* 채팅(Chat, ChatMessage, ChatRoom) */
    CHATROOM_LIST_FETCHED(HttpStatus.OK, "채팅방 목록이 성공적으로 조회되었습니다."),
    CHATROOM_EXITED(HttpStatus.OK, "채팅방에서 성공적으로 나갔습니다."),
    CHAT_MESSAGES_FETCHED(HttpStatus.OK, "채팅 메시지 목록이 성공적으로 조회되었습니다."),
    CHATROOM_ENTERED(HttpStatus.OK, "채팅방에 성공적으로 입장했습니다."),
    CHAT_MESSAGE_SENT(HttpStatus.CREATED, "채팅 메시지가 성공적으로 전송되었습니다."),

    /* 댓글(Comment) */
    COMMENT_CREATED(HttpStatus.CREATED, "댓글이 성공적으로 생성되었습니다."),
    COMMENT_DELETED(HttpStatus.OK, "댓글이 성공적으로 삭제되었습니다."),
    COMMENTS_FETCHED(HttpStatus.OK, "페이퍼의 댓글이 성공적으로 조회되었습니다."),

    /* 페이퍼(Paper) */
    PAPER_SCRAPPED(HttpStatus.OK, "페이퍼가 성공적으로 스크랩되었습니다."),
    PAPER_SHARED(HttpStatus.OK, "페이퍼가 성공적으로 공유되었습니다."),
    PAPER_DELETE(HttpStatus.OK, "책에서 페이지가 삭제되었습니다."),

    /* 쓰레드(Thread) */
    THREAD_FETCHED(HttpStatus.OK, "쓰레드가 성공적으로 조회되었습니다."),
    THREAD_CREATED(HttpStatus.CREATED, "쓰레드가 성공적으로 생성되었습니다."),
    THREAD_UPDATED(HttpStatus.OK, "쓰레드가 성공적으로 업데이트되었습니다."),
    THREAD_DELETED(HttpStatus.OK, "쓰레드가 성공적으로 삭제되었습니다."),
    MAIN_THREAD_LIST_FETCHED(HttpStatus.OK, "메인 홈의 쓰레드 목록이 성공적으로 조회되었습니다."),
    MY_THREAD_LIST_FETCHED(HttpStatus.OK, "나의 쓰레드 목록이 성공적으로 조회되었습니다."),
    OTHER_MEMBER_THREAD_LIST_FETCHED(HttpStatus.OK, "특정 회원의 쓰레드 목록이 성공적으로 조회되었습니다."),

    /* 마이브러리(MyBrary) */
    MYBRARY_FETCHED(HttpStatus.OK, "나의 마이브러리 정보가 성공적으로 조회되었습니다."),
    OTHER_MYBRARY_FETCHED(HttpStatus.OK, "다른 회원의 마이브러리 정보가 성공적으로 조회되었습니다."),
    MYBRARY_UPDATED(HttpStatus.OK, "마이브러리 정보가 성공적으로 업데이트되었습니다."),

    /* 알림(Notification) */
    NOTIFICATION_FETCHED(HttpStatus.OK, "알림 목록이 성공적으로 조회되었습니다."),
    NOTIFICATION_DELETED(HttpStatus.OK, "알림이 성공적으로 삭제되었습니다."),

    /* 검색(Search) */
    POPULAR_KEYWORDS_FETCHED(HttpStatus.OK, "인기 검색어가 성공적으로 조회되었습니다."),
    CONTENTS_SEARCHED(HttpStatus.OK, "컨텐츠 검색 결과가 성공적으로 조회되었습니다."),
    BOOKS_SEARCHED(HttpStatus.OK, "책 검색 결과가 성공적으로 조회되었습니다."),
    ACCOUNTS_SEARCHED(HttpStatus.OK, "계정 검색 결과가 성공적으로 조회되었습니다."),
    MENTION_ACCOUNTS_SEARCHED(HttpStatus.OK, "멘션을 위한 계정 검색 결과가 성공적으로 조회되었습니다."),
    REALTIME_RECOMMENDED_KEYWORDS_FETCHED(HttpStatus.OK, "실시간 추천 검색어가 성공적으로 조회되었습니다."),


    /* 롤링페이퍼(RollingPaper) */
    ROLLING_PAPER_FETCHED(HttpStatus.OK, "롤링페이퍼가 성공적으로 조회되었습니다."),
    ROLLING_PAPER_SAVED(HttpStatus.OK, "롤링페이퍼가 성공적으로 저장되었습니다.");

    private final HttpStatus status;
    private final String message;

}

