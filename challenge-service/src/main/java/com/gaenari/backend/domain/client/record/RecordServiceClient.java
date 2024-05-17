package com.gaenari.backend.domain.client.record;

import com.gaenari.backend.domain.client.record.dto.TotalStatisticDto;
import com.gaenari.backend.global.format.response.GenericResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "exercise-record-service", url = "${feign.exercise-record-service.url}")
public interface RecordServiceClient {

    // 회원 ID를 보내고 누적 기록 가져오기
    @GetMapping("/statistic/feign/{accountId}")
    ResponseEntity<GenericResponse<TotalStatisticDto>> getAllStatistics(@PathVariable(name = "accountId") String accountId);

    // 회원의 도전과제 ID 리스트 조회
    @GetMapping("/record/feign/recordChallenge/{accountId}")
    ResponseEntity<GenericResponse<List<Integer>>> getChallengeIdsByAccountId(@Parameter(name = "회원 ID") @PathVariable(name = "accountId") String accountId);

    // 운동 기록의 도전과제 ID 리스트 조회
    @GetMapping("/record/feign/recordChallenge/{accountId}/{recordId}")
    ResponseEntity<GenericResponse<List<Integer>>> getChallengeIdsByRecordId(@Parameter(name = "회원 ID") @PathVariable(name = "accountId") String accountId,
                                                   @Parameter(name = "운동 기록 ID") @PathVariable(name = "recordId") Long recordId);

    // 운동 기록의 보상 수령 여부 업데이트
    @PutMapping("/record/feign/obtain/{accountId}/{recordId}")
    ResponseEntity<GenericResponse<?>> updateRecordObtained(@Parameter(name = "회원 ID") @PathVariable(name = "accountId") String accountId,
                                                            @Parameter(name = "운동 기록 ID") @PathVariable(name = "recordId") Long recordId);
}
