package com.gaenari.backend.domain.member.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaenari.backend.domain.member.dto.MemberDto;
import com.gaenari.backend.domain.member.dto.requestDto.RequestLogin;
import com.gaenari.backend.global.format.code.ErrorCode;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import com.gaenari.backend.domain.member.service.MemberService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Instant;
import java.util.*;


@Slf4j // 이 필터는 /login 엔드포인트에 자동으로 매핑됨
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final MemberService memberService;
    private final Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                MemberService memberService,
                                Environment env) {
        super(authenticationManager);
        this.memberService = memberService;
        this.env = env;
    }

    @Override // 로그인할때, 호출됨 -> 인증 성공하면 Authentication 객체 반환
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(),
                    RequestLogin.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getAccountId(),
                            creds.getPassword(),
                            new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // 인증을 성공하였을때 처리작업
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
//        String userName = ((User)auth.getPrincipal()).getUsername();
        String accountId = ((User)auth.getPrincipal()).getUsername();

//        UserDto userDetails = memberService.getUserDetailsByEmail(userName); // userName = email
        MemberDto memberDetails = memberService.getMemberDetailsByEmail(accountId);


        byte[] secretKeyBytes = Base64.getEncoder().encode(env.getProperty("token.secret").getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        Instant now = Instant.now();

        // JWT 토큰 생성
        String token = Jwts.builder()
                .subject(memberDetails.getAccountId()) // accountId로 토큰 생성
                // 만료 기한 설정
                .expiration(
                        Date.from(now.plusMillis(Long.parseLong(env.getProperty("token.expiration_time")))))
                // 암호화 설정
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();

        // 응답 본문에 추가할 데이터
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("data", memberDetails); // 현재 인증된 사용자의 정보 추가

        // JSON으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        res.setCharacterEncoding("UTF-8");
        res.addHeader("token", token); // 헤더에 토큰을 넣기

        ApiResponse apiResponse = ApiResponse.getInstance();
        ResponseEntity<?> responseEntity = apiResponse.success(ResponseCode.LOGIN_SUCCESS, memberDetails);
        // 반환
        res.setStatus(responseEntity.getStatusCodeValue());
        res.getWriter().write(new ObjectMapper().writeValueAsString(responseEntity.getBody()));


    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req,
                                              HttpServletResponse res,
                                              AuthenticationException failed) throws IOException, ServletException {
        ApiResponse apiResponse = ApiResponse.getInstance();
        ResponseEntity<?> responseEntity;

        if (failed instanceof BadCredentialsException) {
            // 아이디와 비밀번호가 일치하지 않는 경우의 오류 메시지
            responseEntity = apiResponse.error(ErrorCode.LOGIN_FAILED);
        } else {
            // 다른 인증 실패 경우에 대한 처리 (예: 계정이 잠겨있는 경우 등)
            responseEntity = apiResponse.error(ErrorCode.AUTHENTICATION_FAILED);
        }
        res.setCharacterEncoding("UTF-8");
        res.setStatus(responseEntity.getStatusCodeValue());
        res.getWriter().write(new ObjectMapper().writeValueAsString(responseEntity.getBody()));
    }
}