package com.gaenari.gatewayservice.filter;


import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Base64;


@Component
@Slf4j
// 커스텀 필터라서 AbstractGatewayFilterFactory를 상속받음
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 인증권한이 없다면 에러처리(헤더 안에 토큰이 있는지 확인)
             if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            // 인증 권한이 있다면 가져오기
            // authorizationHeader에는 토큰이 반환됨
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            // 토큰이 진짜인지 검증
            if(!isJwtValid(jwt)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

           return chain.filter(exchange);
        });
    }

    private boolean isJwtValid(String jwt) {
        // jwt가 정상인지 확인
        byte[] secretKeyBytes = Base64.getEncoder().encode(env.getProperty("token.secret").getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        boolean returnValue = true;
        String subject = null;

        try {
            // 토큰에서 subject를 추출한 후 정상적인 계정 값인지 판단
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build();

            subject = jwtParser.parseClaimsJws(jwt).getBody().getSubject();


        }catch (Exception e) {
            returnValue = false;
        }

        if(subject == null || subject.isEmpty()) {
            returnValue = false;
        }



        return returnValue;
    }

    // WebFlux에서 Mono, Flux 데이터 단위가 존재
    // Mono : 단일 값, Flux : 여러 값
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        // response 객체 생성
        ServerHttpResponse response = exchange.getResponse();
        // 필요한 상태코드 저장
        response.setStatusCode(httpStatus);
        // 로그 메세지 출력(에러 메세지)
        log.error(err);
        // setComplete : Mono 타입으로 전달
        return response.setComplete();

    }
}
