package com.gaenari.backend.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaenari.backend.domain.member.service.MemberService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class LogoutHandler implements LogoutSuccessHandler {
    private final MemberService memberService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        // 현재 시간 가져오기
        LocalDateTime logoutTime = LocalDateTime.now();

        // 사용자의 로그아웃 시간 DB에 저장
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        memberService.updateLogoutTime(username, logoutTime);

        // 저장된 시간 불러오기
        LocalDateTime savedTime = memberService.getLastTime(username);

        // 응답에 추가할 데이터 생성
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("lastTime", savedTime);

        // 로그아웃 성공 응답 생성
        ApiResponse apiResponse = ApiResponse.getInstance();
        ResponseEntity<?> responseEntity = apiResponse.success(ResponseCode.LOGOUT_SUCCESS, responseBody);

        // 응답 코드와 메시지 설정
        response.setCharacterEncoding("UTF-8");
        response.setStatus(responseEntity.getStatusCodeValue());
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseEntity.getBody()));
    }
}
