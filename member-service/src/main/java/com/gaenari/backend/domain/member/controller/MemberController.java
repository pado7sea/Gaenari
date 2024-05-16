package com.gaenari.backend.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaenari.backend.domain.coin.dto.requestDto.MemberCoin;
import com.gaenari.backend.domain.coin.dto.requestDto.MemberCoinIncrease;
import com.gaenari.backend.domain.coin.service.CoinService;
import com.gaenari.backend.domain.member.dto.MemberDto;
import com.gaenari.backend.domain.member.dto.requestDto.*;
import com.gaenari.backend.domain.coin.dto.responseDto.MemberCoinHistory;
import com.gaenari.backend.domain.member.dto.responseDto.SignupResponse;
import com.gaenari.backend.domain.member.service.MemberService;
import com.gaenari.backend.global.exception.member.DuplicateEmailException;
import com.gaenari.backend.global.format.code.ErrorCode;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class MemberController {
    private final ApiResponse response;
    private final Environment env;
    private final MemberService memberService;
    private final CoinService coinService;

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
    public ResponseEntity<?> withdrawMember(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId) {
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        // accountId를 사용하여 회원 탈퇴
        memberService.deleteMember(accountId);
        return response.success(ResponseCode.ACCOUNT_SECESSION_SUCCESS);
    }

    @Operation(summary = "회원 닉네임 수정", description = "회원 닉네임 수정")
    @PutMapping("/member/nickname")
    public ResponseEntity<?> updateNick(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId, @RequestParam String nickName){
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        memberService.updateNick(accountId, nickName);
        return response.success(ResponseCode.MEMBER_NICKNAME_UPDATE_SUCCESS);
    }

    @Operation(summary = "회원 비밀번호 확인", description = "비밀번호 변경 전 비밀번호 확인 진행")
    @PostMapping("/member/password")
    public ResponseEntity<?> checkPwd(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId, @RequestBody String password){
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }

        Boolean isRight = memberService.checkPwd(accountId, password);
        int num = 0;
        if(isRight){
            num = 1;
            return response.success(ResponseCode.PASSWORD_CHECK_SUCCESS, num);
        }else{
            num = 0;
            return response.success(ResponseCode.PASSWORD_CHECK_FAIL, num);
        }
    }

    @Operation(summary = "회원 비밀번호 수정", description = "회원 비밀번호 수정")
    @PutMapping("/member/password")
    public ResponseEntity<?> updatePwd(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId, @RequestBody String newPassword){
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        memberService.updatePwd(accountId, newPassword);
        return response.success(ResponseCode.MEMBER_PWD_UPDATE_SUCCESS);
    }
    @Operation(summary = "회원 정보 수정", description = "회원 정보 수정")
    @PutMapping("/member/info")
    public ResponseEntity<?> updateInfo(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId, @RequestBody MemberUpdate memberUpdate){
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        memberService.updateInfo(accountId, memberUpdate);
        return response.success(ResponseCode.MEMBER_INFO_UPDATE_SUCCESS);
    }

    @Operation(summary = "닉네임 중복체크", description = "회원가입시 닉네임 중복체크")
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

    @Operation(summary = "아이디 중복체크", description = "회원가입시 아이디 중복체크")
    @GetMapping("/dupl/accountId")
    public ResponseEntity<?> duplEmail(@RequestParam String accountId){
        Boolean isDuplAccountId = memberService.duplAccountIdCheck(accountId);
        if(isDuplAccountId){
            // true : 사용중
            return ApiResponse.getInstance().success(ResponseCode.ALREADY_USE_EMAIL, isDuplAccountId);
        }else{
            // false : 사용가능한
            return ApiResponse.getInstance().success(ResponseCode.AVAILABLE_EMAIL, isDuplAccountId);
        }
    }

    @Operation(summary = "워치 연동인증번호 발급", description = "워치 연동인증번호 발급")
    @GetMapping("watch")
    public ResponseEntity<?>  issuedAuthNum(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId){
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }

        String authNum = memberService.issuedAuthCode(accountId);
        return response.success(ResponseCode.ISSUED_WATCH_AUTH_CODE_SUCCESS, authNum);
    }

    @Operation(summary = "워치 연동인증번호 등록", description = "워치 연동인증번호 등록")
    @PostMapping("watch")
    public ResponseEntity<?> connectWatch(@RequestParam String authCode) throws JsonProcessingException {
        MemberDto memberDetails = memberService.checkAuthCode(authCode);
        // 회원 토큰 생성
        byte[] secretKeyBytes = Base64.getEncoder().encode(env.getProperty("token.secret").getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
        Instant now = Instant.now();

        // JWT 토큰 생성
        String token = Jwts.builder()
                .subject(memberDetails.getAccountId()) // memberAccountId로 토큰 생성
                // 암호화 설정
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();

        // 헤더에 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        // 응답 데이터 생성
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", "SUCCESS");
        responseBody.put("message", "워치 연동이 정상적으로 이루어졌습니다.");
        responseBody.put("data", memberDetails);

        // 응답에 헤더와 데이터를 함께 반환 (JSON 형식으로)
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)  // JSON형식으로 반환
                .headers(headers)
                .body(responseBody);
    }

    @Operation(summary = "[Feign] 회원 체중 조회", description = "Feign API")
    @GetMapping("/member/weight/{accountId}")
    public ResponseEntity<?> getWeight(@PathVariable String accountId){
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        int weight = memberService.getWeight(accountId);
        return response.success(ResponseCode.MEMBER_INFO_GET_SUCCESS, weight);
    }

}
