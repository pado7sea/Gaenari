package com.gaenari.backend.domain.member.service;

import com.gaenari.backend.domain.member.dto.MemberDto;
import com.gaenari.backend.domain.coin.dto.requestDto.MemberCoin;
import com.gaenari.backend.domain.member.dto.requestDto.MemberUpdate;
import com.gaenari.backend.domain.member.dto.requestDto.SignupRequestDto;
import com.gaenari.backend.domain.coin.dto.responseDto.MemberCoinHistory;
import com.gaenari.backend.domain.member.dto.responseDto.SignupResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    SignupResponse createMember(SignupRequestDto requestDto); // 회원가입
    MemberDto getMemberDetailsByEmail(String memberEmail); // 이메일로 회원찾기
    void deleteMember(String memberEmail); // 회원 삭제
    void updateNick(String memberEmail, String nickName); // 회원 닉네임 변경
    Boolean checkPwd(String memberEmail,String password); // 비밀번호 확인
    void updatePwd(String memberEmail, String newPassword); // 회원 비밀번호 변경
    void updateInfo(String memberEmail, MemberUpdate memberUpdate); // 회원 정보 변경(키,몸무게)
    Boolean duplNickNameCheck(String nickName); // 닉네임 중복체크
    Boolean duplEmailCheck(String email); // 이메일 중복체크
    String issuedAuthCode(String memberEmail); // 워치 인증번호 발급
    MemberDto checkAuthCode(String authCode); // 워치 인증번호 확인
    int getWeight(String memberEmail); // 회원 체중 조회
    String getMemberEmail(Long mateId); // 회원 이메일 조회

}
