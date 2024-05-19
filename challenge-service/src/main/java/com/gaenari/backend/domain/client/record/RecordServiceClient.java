package com.gaenari.backend.domain.client.record;

import com.gaenari.backend.domain.client.record.dto.TotalStatisticDto;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.GenericResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "exercise-record-service", url = "${feign.url.exercise-record-service}")
public interface RecordServiceClient {

    // 회원 ID를 보내고 누적 기록 가져오기
    @GetMapping("/statistic/feign/{accountId}")
    @CircuitBreaker(name = "exercise-record-service-circuit-breaker", fallbackMethod = "fallbackForGetAllStatistics")
    ResponseEntity<GenericResponse<TotalStatisticDto>> getAllStatistics(@PathVariable(name = "accountId") String accountId);

    // 회원의 도전과제 ID 리스트 조회
    @GetMapping("/record/feign/recordChallenge/{accountId}")
    @CircuitBreaker(name = "exercise-record-service-circuit-breaker", fallbackMethod = "fallbackForGetChallengeIdsByAccountId")
    ResponseEntity<GenericResponse<List<Integer>>> getChallengeIdsByAccountId(@Parameter(name = "회원 ID") @PathVariable(name = "accountId") String accountId);

    // 운동 기록의 도전과제 ID 리스트 조회
    @GetMapping("/record/feign/recordChallenge/{accountId}/{recordId}")
    @CircuitBreaker(name = "exercise-record-service-circuit-breaker", fallbackMethod = "fallbackForGetChallengeIdsByRecordId")
    ResponseEntity<GenericResponse<List<Integer>>> getChallengeIdsByRecordId(@Parameter(name = "회원 ID") @PathVariable(name = "accountId") String accountId,
                                                                             @Parameter(name = "운동 기록 ID") @PathVariable(name = "recordId") Long recordId);

    // 운동 기록의 보상 수령 여부 업데이트
    @PutMapping("/record/feign/obtain/{accountId}/{recordId}")
    @CircuitBreaker(name = "exercise-record-service-circuit-breaker", fallbackMethod = "fallbackForUpdateRecordObtained")
    ResponseEntity<GenericResponse<?>> updateRecordObtained(@Parameter(name = "회원 ID") @PathVariable(name = "accountId") String accountId,
                                                            @Parameter(name = "운동 기록 ID") @PathVariable(name = "recordId") Long recordId);

    // 모든 운동 기록의 보상 수령 여부 업데이트
    @PutMapping("/record/feign/obtain/{accountId}")
    @CircuitBreaker(name = "exercise-record-service-circuit-breaker", fallbackMethod = "fallbackForUpdateAllRecordObtained")
    ResponseEntity<GenericResponse<?>> updateAllRecordObtained(@Parameter(name = "회원 ID") @PathVariable(name = "accountId") String accountId);


    default ResponseEntity<GenericResponse<TotalStatisticDto>> fallbackForGetAllStatistics(String accountId, Throwable t) {
        logError("getAllStatistics", accountId, null, t);
        TotalStatisticDto fallbackStatistic = TotalStatisticDto.builder()
                .time(0.0)
                .dist(0.0)
                .cal(0.0)
                .pace(0.0)
                .date(null)
                .count(0)
                .build();
        GenericResponse<TotalStatisticDto> response = GenericResponse.<TotalStatisticDto>builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(fallbackStatistic)
                .build();
        return ResponseEntity.ok(response);
    }

    default ResponseEntity<GenericResponse<List<Integer>>> fallbackForGetChallengeIdsByAccountId(String accountId, Throwable t) {
        logError("getChallengeIdsByAccountId", accountId, null, t);
        GenericResponse<List<Integer>> response = GenericResponse.<List<Integer>>builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(Collections.emptyList())
                .build();
        return ResponseEntity.ok(response);
    }

    default ResponseEntity<GenericResponse<List<Integer>>> fallbackForGetChallengeIdsByRecordId(String accountId, Long recordId, Throwable t) {
        logError("getChallengeIdsByRecordId", accountId, recordId, t);
        GenericResponse<List<Integer>> response = GenericResponse.<List<Integer>>builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(Collections.emptyList())
                .build();
        return ResponseEntity.ok(response);
    }

    default ResponseEntity<GenericResponse<?>> fallbackForUpdateRecordObtained(String accountId, Long recordId, Throwable t) {
        logError("updateRecordObtained", accountId, recordId, t);
        GenericResponse<?> response = GenericResponse.builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    default ResponseEntity<GenericResponse<?>> fallbackForUpdateAllRecordObtained(String accountId, Throwable t) {
        logError("updateRecordObtained", accountId, t);
        GenericResponse<?> response = GenericResponse.builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    private void logError(String methodName, String accountId, Long recordId, Throwable t) {
        // 폴백 로그를 찍을 때 사용할 메서드
        System.err.println(String.format("Fallback method called for %s with accountId: %s and recordId: %s, error: %s", methodName, accountId, recordId, t.getMessage()));
    }

    private void logError(String methodName, String accountId, Throwable t) {
        // 폴백 로그를 찍을 때 사용할 메서드
        System.err.println(String.format("Fallback method called for %s with accountId: %s, error: %s", methodName, accountId, t.getMessage()));
    }
}
