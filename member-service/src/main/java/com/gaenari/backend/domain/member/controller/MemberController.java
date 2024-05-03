package com.gaenari.backend.domain.member.controller;

import com.gaenari.backend.domain.member.dto.requestDto.MemberUpdate;
import com.gaenari.backend.domain.member.dto.requestDto.RequestLogin;
import com.gaenari.backend.domain.member.dto.requestDto.SignupRequestDto;
import com.gaenari.backend.domain.member.dto.responseDto.SignupResponse;
import com.gaenari.backend.domain.member.service.MemberService;
import com.gaenari.backend.global.exception.member.DuplicateEmailException;
import com.gaenari.backend.global.format.code.ErrorCode;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class MemberController {
    private final ApiResponse response;
    private final Environment env;
    private final MemberService memberService;

    @GetMapping("/health_check") // 연결 확인
    public String status() {
        return String.format("It's Working in Member service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @Operation(summary = "회원가입", description = "아이디 최대 50글자, 닉네임 최대 10글자, 비밀번호 최대 12글자(대소문자구분)")
    @PostMapping("/members") // 회원가입
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto){
        try {
            SignupResponse saved = memberService.createMember(requestDto);
            return ApiResponse.getInstance().success(ResponseCode.MEMBER_SIGNUP_SUCCESS, saved);
        } catch (DuplicateEmailException e) {
            return ApiResponse.getInstance().error(e.getErrorCode());
        } catch (Exception e) {
            return ApiResponse.getInstance().error(ErrorCode.GLOBAL_UNEXPECTED_ERROR);
        }
    }

    @Operation(summary = "로그인", description = "옛다 로그인")
    @PostMapping("/login") // 로그인
    public ResponseEntity<?> login(@RequestBody RequestLogin login){
        return ApiResponse.getInstance().success(ResponseCode.MEMBER_SIGNUP_SUCCESS);
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 (삭제처리)")
    @DeleteMapping("/leave")
    public ResponseEntity<?> withdrawMember(@RequestHeader("User-Info") String memberEmail) {
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        // memberId를 사용하여 회원 탈퇴
        memberService.deleteMember(memberEmail);
        return response.success(ResponseCode.ACCOUNT_SECESSION_SUCCESS);
    }

    @Operation(summary = "회원 닉네임 수정", description = "회원 닉네임 수정")
    @PutMapping("/member/nickname")
    public ResponseEntity<?> updateNick(@RequestHeader("User-Info") String memberEmail, @RequestParam String nickName){
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        memberService.updateNick(memberEmail, nickName);
        return response.success(ResponseCode.MEMBER_NICKNAME_UPDATE_SUCCESS);
    }
    @Operation(summary = "회원 비밀번호 수정", description = "회원 비밀번호 수정")
    @PutMapping("/member/password")
    public ResponseEntity<?> updatePwd(@RequestHeader("User-Info") String memberEmail, @RequestBody String newPassword){
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        memberService.updatePwd(memberEmail, newPassword);
        return response.success(ResponseCode.MEMBER_PWD_UPDATE_SUCCESS);
    }
    @Operation(summary = "회원 정보 수정", description = "회원 정보 수정")
    @PutMapping("/member/info")
    public ResponseEntity<?> updateInfo(@RequestHeader("User-Info") String memberEmail, @RequestBody MemberUpdate memberUpdate){
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        memberService.updateInfo(memberEmail, memberUpdate);
        return response.success(ResponseCode.MEMBER_INFO_UPDATE_SUCCESS);
    }

    @Operation(summary = "회원보유코인 조회", description = "회원보유코인 조회")
    @GetMapping("/member/coin")
    public ResponseEntity<?> getMemberCoin(@RequestHeader("User-Info") String memberEmail) {
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        int Coin = memberService.getCoin(memberEmail);
        return response.success(ResponseCode.COIN_FETCH_SUCCESS, Coin);
    }

    @Operation(summary = "닉네임 중복체크", description = "닉네임 중복체크")
    @GetMapping("/dupl/nickname")
    public ResponseEntity<?> duplNickName(@RequestParam String nickName){
        Boolean isDuplNickName = memberService.duplNickNameCheck(nickName);
        if(isDuplNickName){
            // 사용중
            return response.success(ResponseCode.ALREADY_USE_NICKNAME, isDuplNickName);
        }else{
            // 사용가능한
            return response.success(ResponseCode.AVAILABLE_NICKNAME, isDuplNickName);
        }
    }

    @Operation(summary = "아이디 중복체크", description = "아이디 중복체크")
    @GetMapping("/dupl/email")
    public ResponseEntity<?> duplEmail(@RequestParam String email){
        Boolean isDuplEmail = memberService.duplEmailCheck(email);
        if(isDuplEmail){
            // true : 사용중
            return ApiResponse.getInstance().success(ResponseCode.ALREADY_USE_EMAIL, isDuplEmail);
        }else{
            // false : 사용가능한
            return ApiResponse.getInstance().success(ResponseCode.AVAILABLE_EMAIL, isDuplEmail);
        }
    }


}
