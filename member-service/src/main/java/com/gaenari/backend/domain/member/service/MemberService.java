package com.gaenari.backend.domain.member.service;

import com.gaenari.backend.domain.member.dto.MemberDto;
import com.gaenari.backend.domain.member.dto.requestDto.MemberUpdate;
import com.gaenari.backend.domain.member.dto.requestDto.SignupRequestDto;
import com.gaenari.backend.domain.member.dto.responseDto.SignupResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;

public interface MemberService extends UserDetailsService {
    SignupResponse createMember(SignupRequestDto requestDto); // 회원가입

    MemberDto getMemberDetailsByEmail(String memberEmail); // 이메일로 회원찾기
    void updateLogoutTime(String memberEmail, LocalDateTime logoutTime); // 로그아웃시 시간저장

    LocalDateTime getLastTime(String memberEmail); // 마지막 접속시간 반환
    void deleteMember(String memberEmail); // 회원 삭제
    int getCoin(String memberEmail); // 보유코인조회
    void updateNick(String memberEmail, String nickName); // 회원 닉네임 변경
    void updatePwd(String memberEmail, String newPassword); // 회원 비밀번호 변경
    void updateInfo(String memberEmail, MemberUpdate memberUpdate); // 회원 정보 변경(키,몸무게)
}
