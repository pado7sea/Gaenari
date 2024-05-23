package com.gaenari.backend.domain.client.challenge;

import com.gaenari.backend.domain.client.challenge.dto.ChallengeDto;
import com.gaenari.backend.domain.client.challenge.dto.RecordAboutChallengeDto;
import com.gaenari.backend.domain.client.challenge.dto.RewardDto;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.GenericResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "challenge-service", url = "${feign.url.challenge-service}")
public interface ChallengeServiceClient {

    // 운동 기록을 보내고 새로 달성한 도전과제 ID 리스트 받아오기
    @PostMapping("/achieve/feign/challengeIds")
    @CircuitBreaker(name = "challenge-service-circuit-breaker", fallbackMethod = "fallbackForGetNewlyAchievedChallengeIds")
    ResponseEntity<GenericResponse<List<Integer>>> getNewlyAchievedChallengeIds(@RequestBody RecordAboutChallengeDto recordDto);

    // 도전과제 ID 리스트를 보내고 도전과제 리스트로 받기
    @PostMapping("/achieve/feign/challenges")
    @CircuitBreaker(name = "challenge-service-circuit-breaker", fallbackMethod = "fallbackForGetChallenges")
    ResponseEntity<GenericResponse<List<ChallengeDto>>> getChallenges(@RequestBody List<Integer> challengeIds);

    // 도전과제 ID 로 얻을 수 있는 코인, 애정도 받기
    @GetMapping("/reward/feign/{accountId}/record/{recordId}")
    @CircuitBreaker(name = "challenge-service-circuit-breaker", fallbackMethod = "fallbackForGetAttainableRewards")
    ResponseEntity<GenericResponse<RewardDto>> getAttainableRewards(@PathVariable String accountId, @PathVariable Long recordId);

    default ResponseEntity<GenericResponse<List<Integer>>> fallbackForGetNewlyAchievedChallengeIds(RecordAboutChallengeDto recordDto, Throwable t) {
        logError("getNewlyAchievedChallengeIds", recordDto, t);
        GenericResponse<List<Integer>> response = GenericResponse.<List<Integer>>builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(Collections.emptyList())
                .build();
        return ResponseEntity.ok(response);
    }

    default ResponseEntity<GenericResponse<List<ChallengeDto>>> fallbackForGetChallenges(List<Integer> challengeIds, Throwable t) {
        logError("getChallenges", challengeIds, t);
        ChallengeDto fallbackChallenge = ChallengeDto.builder()
                .id(0)
                .category(null)
                .type(null)
                .value(0)
                .coin(0)
                .heart(0)
                .build();
        GenericResponse<List<ChallengeDto>> response = GenericResponse.<List<ChallengeDto>>builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(Collections.emptyList())
                .build();
        return ResponseEntity.ok(response);
    }

    default ResponseEntity<GenericResponse<RewardDto>> fallbackForGetAttainableRewards(String accountId, Long recordId, Throwable t) {
        logError("getAttainableRewards", accountId, recordId, t);
        RewardDto fallbackReward = RewardDto.builder()
                .accountId(accountId)
                .coin(0)
                .heart(0)
                .build();
        GenericResponse<RewardDto> response = GenericResponse.<RewardDto>builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(fallbackReward)
                .build();
        return ResponseEntity.ok(response);
    }

    private void logError(String methodName, Object requestDto, Throwable t) {
        // 폴백 로그를 찍을 때 사용할 메서드
        System.err.println(String.format("Fallback method called for %s with requestDto: %s, error: %s", methodName, requestDto, t.getMessage()));
    }

    private void logError(String methodName, String accountId, Long recordId, Throwable t) {
        System.err.println(String.format("Fallback method called for %s with accountId: %s, recordId: %s, error: %s", methodName, accountId, recordId, t.getMessage()));
    }
}
