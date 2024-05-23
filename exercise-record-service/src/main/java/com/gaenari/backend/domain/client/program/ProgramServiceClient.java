package com.gaenari.backend.domain.client.program;

import com.gaenari.backend.domain.client.program.dto.ProgramDetailAboutRecordDto;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.GenericResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "program-service", url = "${feign.url.program-service}")
public interface ProgramServiceClient {

    @GetMapping("/program/feign/{programId}")
    @CircuitBreaker(name = "program-service-circuit-breaker")
    ResponseEntity<GenericResponse<ProgramDetailAboutRecordDto>> getProgramDetailById(@PathVariable("programId") Long programId);

    @PutMapping("/program/feign/use/{programId}")
    @CircuitBreaker(name = "program-service-circuit-breaker")
    ResponseEntity<GenericResponse<Integer>> updateProgramUsageCount(@PathVariable("programId") Long programId);

    default ResponseEntity<GenericResponse<ProgramDetailAboutRecordDto>> fallbackForGetProgramDetailById(Long programId) {
        logError("getProgramDetailById", programId, null);
        ProgramDetailAboutRecordDto fallbackProgramDetail = ProgramDetailAboutRecordDto.builder()
                .programId(0L) // 기본값
                .programTitle("기본 운동 프로그램")
                .isFavorite(false)
                .type(null)
                .program(null)
                .usageCount(0)
                .build();
        GenericResponse<ProgramDetailAboutRecordDto> response = GenericResponse.<ProgramDetailAboutRecordDto>builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(fallbackProgramDetail)
                .build();
        return ResponseEntity.ok(response);
    }

    default ResponseEntity<GenericResponse<Integer>> fallbackForUpdateProgramUsageCount(Long programId) {
        logError("updateProgramUsageCount", programId, null);
        GenericResponse<Integer> response = GenericResponse.<Integer>builder()
                .status("FALLBACK")
                .message(ResponseCode.FALLBACK_SUCCESS.getMessage())
                .data(-1) // 업데이트 된 카운트가 아니라 -1 반환
                .build();
        return ResponseEntity.ok(response);
    }

    private void logError(String methodName, Long programId, Throwable t) {
        // 폴백 로그를 찍을 때 사용할 메서드
        System.err.println(String.format("Fallback method called for %s with programId: %s, error: %s", methodName, programId, t != null ? t.getMessage() : "unknown"));
    }

}
