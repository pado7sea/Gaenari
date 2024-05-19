package com.gaenari.backend.domain.client;

import com.gaenari.backend.domain.client.dto.MemberCoin;
import com.gaenari.backend.domain.item.dto.responseDto.Pets;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.GenericResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "member-service", url = "${feign.url.member-service}")
public interface MemberServiceClient {
    @CircuitBreaker(name = "member-service-circuit-breaker", fallbackMethod = "getPetsFallback")
    @GetMapping("/pet/{accountId}") // 회원이 가지고 있는 펫 조회
    ResponseEntity<GenericResponse<List<Pets>>> getPets(@PathVariable String accountId);
    @CircuitBreaker(name = "member-service-circuit-breaker", fallbackMethod = "getDogPriceFallback")
    @GetMapping("/pet/dog/{dogId}") // 강아지 가격 조회
    ResponseEntity<GenericResponse<?>> getDogPrice(@PathVariable int dogId);
    @CircuitBreaker(name = "member-service-circuit-breaker", fallbackMethod = "getMateAccountIdFallback")
    @GetMapping("/mate/accountId/{memberId}") // 친구 아이디 조회
    ResponseEntity<GenericResponse<?>> getMateAccountId(@PathVariable Long memberId);
    @CircuitBreaker(name = "member-service-circuit-breaker", fallbackMethod = "updateCoinFallback")
    @PutMapping("/coin") // 회원 코인 증/감
    ResponseEntity<GenericResponse<?>> updateCoin(@RequestBody MemberCoin memberCoin);
    @CircuitBreaker(name = "member-service-circuit-breaker", fallbackMethod = "getMemberCoinFallback")
    @GetMapping("/coin/{accountId}") // 회원보유코인 조회
    ResponseEntity<GenericResponse<?>> getMemberCoin(@PathVariable String accountId);

    default ResponseEntity<GenericResponse<?>> getPetsFallback(String accountId, Throwable e){
        logError("getPets", accountId, e);
        List<Pets> petsList = new ArrayList<>();
        Pets pets = Pets.builder()
                .id(0L)
                .name("프로그램이 로드되지 않았습니다.")
                .affection((long) 0)
                .tier(null)
                .isPartner(false)
                .price(0)
                .build();
        petsList.add(pets);
        GenericResponse<?> response = GenericResponse.builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(petsList) // 대체 데이터 설정
                .build();
        return ResponseEntity.ok(response); // ResponseEntity로 감싸서 반환
    }
    default ResponseEntity<GenericResponse<?>> getDogPriceFallback(int dogId, Throwable e){
        logError("getDogPrice", dogId, e);
        GenericResponse<?> response = GenericResponse.builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(0) // 대체 데이터 설정 : 강아지 금액 0코인으로 반환
                .build();
        return ResponseEntity.ok(response); // ResponseEntity로 감싸서 반환
    }
    default ResponseEntity<GenericResponse<?>> getMateAccountIdFallback(Long memberId, Throwable e){
        logError("getMateAccountId", memberId, e);
        GenericResponse<?> response = GenericResponse.builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data("admin") // 대체 데이터 설정 : accountId로 admin 반환
                .build();
        return ResponseEntity.ok(response); // ResponseEntity로 감싸서 반환
    }
    default ResponseEntity<GenericResponse<?>> updateCoinFallback(MemberCoin memberCoin, Throwable e){
        logError("updateCoin", memberCoin, e);
        GenericResponse<?> response = GenericResponse.builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(null) // 대체 데이터 설정 : void
                .build();
        return ResponseEntity.ok(response); // ResponseEntity로 감싸서 반환
    }
    default ResponseEntity<GenericResponse<?>> getMemberCoinFallback(String accountId, Throwable e){
        logError("getMemberCoin", accountId, e);
        GenericResponse<?> response = GenericResponse.builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(0) // 대체 데이터 설정 : 0코인 반환
                .build();
        return ResponseEntity.ok(response); // ResponseEntity로 감싸서 반환
    }

    private void logError(String methodName, String accountId, Throwable t) {
        // 폴백 로그를 찍을 때 사용할 메서드
        System.err.println(String.format("Fallback method called for %s with accountId: %s, error: %s", methodName, accountId, t.getMessage()));
    }
    private void logError(String methodName, int dogId, Throwable t) {
        // 폴백 로그를 찍을 때 사용할 메서드
        System.err.println(String.format("Fallback method called for %s with dogId: %s, error: %s", methodName, dogId, t.getMessage()));
    }
    private void logError(String methodName, Object responseDto, Throwable t) {
        // 폴백 로그를 찍을 때 사용할 메서드
        System.err.println(String.format("Fallback method called for %s with responseDto: %s, error: %s", methodName, responseDto, t.getMessage()));
    }
}
