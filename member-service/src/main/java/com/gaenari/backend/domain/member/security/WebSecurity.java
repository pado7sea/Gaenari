package com.gaenari.backend.domain.member.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaenari.backend.domain.member.service.MemberService;
import com.gaenari.backend.global.format.code.ErrorCode;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // AuthenticationManagerBuilder 가져오기
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        // 사용자 정보 조회할 서비스, 비밀번호 인코딩 설정
        // memberService에 extends UserDetailsService 추가하기
        authenticationManagerBuilder.userDetailsService(memberService).passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        // csrf 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // http 요청에 대한 권한 설정
        http.authorizeHttpRequests((authz) -> authz
                        // requestMatchers : 특정 요청 경로에 대한 접근 설정, access : 접근 권한 지정

                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 인증 관리자 설정
                .authenticationManager(authenticationManager)
                // 세션 관리 정책 설정
                .sessionManagement((session) -> session
                        // 세션을 생성하지 않는 STATELESS 정책을 설정
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // addFilter : 사용자 정의 인증 필터 추가
        http.addFilter(getAuthenticationFilter(authenticationManager));
        // headers : 응답 헤더 설정 지정
//        http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));

        // 로그아웃 설정
        http.logout(logout -> {
            logout.logoutUrl("/logout") // 로그아웃을 처리할 엔드포인트 설정
                    .logoutSuccessUrl("/") // 로그아웃 성공 후 리디렉션될 URL 설정
                    .invalidateHttpSession(true) // HTTP 세션 무효화
                    .deleteCookies("JSESSIONID") // 쿠키 삭제
                    .logoutSuccessHandler((request, response, authentication) -> {
                        // 헤더에서 accountId 추출
                        String accountId = request.getHeader("memberId");

                        if(accountId == null) {
                            ApiResponse apiResponse = ApiResponse.getInstance();
                            ResponseEntity<?> responseEntity = apiResponse.error(ErrorCode.AUTHENTICATION_FAILED);
                            response.setCharacterEncoding("UTF-8");
                            response.setStatus(responseEntity.getStatusCodeValue());
                            response.getWriter().write(new ObjectMapper().writeValueAsString(responseEntity.getBody()));
                            return;
                        }

//                        // 현재 시간 가져오기
//                        LocalDateTime logoutTime = LocalDateTime.now();
//
//                        // 사용자의 로그아웃 시간 DB에 저장
//                        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//                        memberService.updateLogoutTime(username, logoutTime);
//
//                        // 저장된 시간 불러오기
//                        LocalDateTime savedTime = memberService.getLastTime(username);
//
//                        // 응답에 추가할 데이터 생성
//                        Map<String, Object> responseBody = new HashMap<>();
//                        responseBody.put("lastTime", savedTime);

                        // 로그아웃 성공 응답 생성
                        ApiResponse apiResponse = ApiResponse.getInstance();
//                        ResponseEntity<?> responseEntity = apiResponse.success(ResponseCode.LOGOUT_SUCCESS, responseBody);
                        ResponseEntity<?> responseEntity = apiResponse.success(ResponseCode.LOGOUT_SUCCESS);

                        // 응답 코드와 메시지 설정
                        response.setCharacterEncoding("UTF-8");
                        response.setStatus(responseEntity.getStatusCodeValue());
                        response.getWriter().write(new ObjectMapper().writeValueAsString(responseEntity.getBody()));
                    })
                    .permitAll(); // 모든 사용자에게 로그아웃 허용
        });



        return http.build();
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        return new AuthenticationFilter(authenticationManager, memberService, env);
    }
}
