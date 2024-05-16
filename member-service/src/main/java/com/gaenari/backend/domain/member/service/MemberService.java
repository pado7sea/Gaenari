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
    MemberDto getMemberDetailsByEmail(String accountId); // 이메일로 회원찾기
    void deleteMember(String accountId); // 회원 삭제
    void updateNick(String accountId, String nickName); // 회원 닉네임 변경
    Boolean checkPwd(String accountId,String password); // 비밀번호 확인
    void updatePwd(String accountId, String newPassword); // 회원 비밀번호 변경
    void updateInfo(String accountId, MemberUpdate memberUpdate); // 회원 정보 변경(키,몸무게)
    Boolean duplNickNameCheck(String nickName); // 닉네임 중복체크
    Boolean duplAccountIdCheck(String accountId); // 아이디 중복체크
    String issuedAuthCode(String accountId); // 워치 인증번호 발급
    MemberDto checkAuthCode(String authCode); // 워치 인증번호 확인
    int getWeight(String accountId); // 회원 체중 조회
    String getMemberAccountId(Long mateId); // 회원 아이디 조회

}
