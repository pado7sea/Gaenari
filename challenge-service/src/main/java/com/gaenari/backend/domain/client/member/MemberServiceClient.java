package com.gaenari.backend.domain.client.member;

import com.gaenari.backend.domain.client.member.dto.HeartChangeDto;
import com.gaenari.backend.domain.client.member.dto.MemberCoinDto;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.GenericResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "member-service", url = "${feign.url.member-service}")
public interface MemberServiceClient {

    // 회원 코인 업데이트
    @PutMapping("/coin")
    @CircuitBreaker(name = "member-service-circuit-breaker", fallbackMethod = "fallbackForUpdateCoin")
    ResponseEntity<GenericResponse<?>> updateCoin(@RequestBody MemberCoinDto memberCoinDto);

    // 파트너 펫 애정도 업데이트
    @PutMapping("/pet/heart")
    @CircuitBreaker(name = "member-service-circuit-breaker", fallbackMethod = "fallbackForUpdateHeart")
    ResponseEntity<GenericResponse<?>> updateHeart(@RequestBody HeartChangeDto heartChangeDto);

    default ResponseEntity<GenericResponse<?>> fallbackForUpdateCoin(MemberCoinDto memberCoinDto, Throwable t) {
        logError("updateCoin", memberCoinDto, t);
        GenericResponse<?> response = GenericResponse.builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    default ResponseEntity<GenericResponse<?>> fallbackForUpdateHeart(HeartChangeDto heartChangeDto, Throwable t) {
        logError("updateHeart", heartChangeDto, t);
        GenericResponse<?> response = GenericResponse.builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    private void logError(String methodName, Object requestDto, Throwable t) {
        // 폴백 로그를 찍을 때 사용할 메서드
        System.err.println(String.format("Fallback method called for %s with requestDto: %s, error: %s", methodName, requestDto, t.getMessage()));
    }

}
