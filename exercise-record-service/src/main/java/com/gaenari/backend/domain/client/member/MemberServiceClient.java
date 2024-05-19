package com.gaenari.backend.domain.client.member;

import com.gaenari.backend.domain.client.member.dto.HeartChangeDto;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.GenericResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "member-service", url = "${feign.url.member-service}")
public interface MemberServiceClient {

    // 회원 체중 조회
    @GetMapping("/member/weight/{accountId}")
    @CircuitBreaker(name = "member-service-circuit-breaker", fallbackMethod = "fallbackForGetWeight")
    ResponseEntity<GenericResponse<Integer>> getWeight(@PathVariable(name = "accountId") String accountId);

    // 파트너 펫 애정도 업데이트
    @PutMapping("/pet/heart")
    @CircuitBreaker(name = "member-service-circuit-breaker", fallbackMethod = "fallbackForUpdateHeart")
    ResponseEntity<GenericResponse<?>> updateHeart(@RequestBody HeartChangeDto heartChangeDto);

    default ResponseEntity<GenericResponse<Integer>> fallbackForGetWeight(String accountId, Throwable t) {
        weightLogError("getWeight", accountId, t);
        GenericResponse<Integer> response = GenericResponse.<Integer>builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(60) // 기본값으로 성인(19세 이상) 평균 체중 60을 반환
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

    private void weightLogError(String methodName, String accountId, Throwable t) {
        // 폴백 로그를 찍을 때 사용할 메서드
        System.err.println(String.format("Fallback method called for %s with accountId: %s, error: %s", methodName, accountId, t.getMessage()));
    }

    private void logError(String methodName, Object requestDto, Throwable t) {
        System.err.println(String.format("Fallback method called for %s with requestDto: %s, error: %s", methodName, requestDto, t.getMessage()));
    }


}