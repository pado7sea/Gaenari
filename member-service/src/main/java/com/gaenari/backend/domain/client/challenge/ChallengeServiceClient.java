package com.gaenari.backend.domain.client.challenge;

import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import com.gaenari.backend.global.format.response.GenericResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "challenge-service", url = "${feign.url.challenge-service}")
public interface ChallengeServiceClient {
    @CircuitBreaker(name = "challenge-service-circuit-breaker", fallbackMethod = "isGetRewardFallback")
    @GetMapping("/reward/notice/{accountId}") // 받지 않은 보상 여부
    ResponseEntity<GenericResponse<Boolean>> isGetReward(@PathVariable String accountId);

    default ResponseEntity<GenericResponse<Boolean>> isGetRewardFallback(String accountId, Throwable e){
        GenericResponse<Boolean> response = GenericResponse.<Boolean>builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(true) // 대체 데이터 설정
                .build();
        return ResponseEntity.ok(response); // ResponseEntity로 감싸서 반환
    }

}
