package com.gaenari.backend.domain.client.inventory;

import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.GenericResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventory-service", url = "${feign.url.inventory-service}")
public interface InventoryServiceClient {
    @CircuitBreaker(name = "inventory-service-circuit-breaker", fallbackMethod = "createNormalItemsFallback")
    @PostMapping("/inventory/items/{accountId}") // 회원가입시 기본 아이템 생성
    ResponseEntity<GenericResponse<?>> createNormalItems(@PathVariable String accountId);
    @CircuitBreaker(name = "inventory-service-circuit-breaker", fallbackMethod = "deleteItemsFallback")
    @DeleteMapping("/inventory/items/{accountId}") // 회원탈퇴시 아이템 삭제
    ResponseEntity<GenericResponse<?>> deleteItems(@PathVariable String accountId);

    default ResponseEntity<GenericResponse<?>> createNormalItemsFallback(String accountId, Throwable e){
        logError("createNormalItems", accountId, e);
        GenericResponse<?> response = GenericResponse.builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(null) // 대체 데이터 설정
                .build();
        return ResponseEntity.ok(response); // ResponseEntity로 감싸서 반환
    }
    default ResponseEntity<GenericResponse<?>> deleteItemsFallback(String accountId, Throwable e){
        logError("deleteItems", accountId, e);
        GenericResponse<?> response = GenericResponse.builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(null) // 대체 데이터 설정
                .build();
        return ResponseEntity.ok(response); // ResponseEntity로 감싸서 반환
    }

    private void logError(String methodName, String accountId, Throwable t) {
        // 폴백 로그를 찍을 때 사용할 메서드
        System.err.println(String.format("Fallback method called for %s with accountId: %s, error: %s", methodName, accountId, t.getMessage()));
    }
}
