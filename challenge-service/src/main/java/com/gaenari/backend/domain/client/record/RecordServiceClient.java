package com.gaenari.backend.domain.client.record;

import com.gaenari.backend.domain.client.record.dto.TotalStatisticDto;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "exercise-record-service", url = "${feign.exercise-record-service.url}")
public interface RecordServiceClient {

    // 회원 ID를 보내고 누적 기록 가져오기
    @GetMapping("/statistic/feign/{memberId}")
    TotalStatisticDto getAllStatistics(@PathVariable(name = "memberId") String memberId);

    // 회원의 도전과제 ID 리스트 조회
    @GetMapping("/record/feign/recordChallenge/{memberId}")
    List<Integer> getChallengeIdsByMemberId(@Parameter(name = "회원 ID") @PathVariable(name = "memberId") String memberId);

    // 운동 기록의 도전과제 ID 리스트 조회
    @GetMapping("/record/feign/recordChallenge/{memberId}/{recordId}")
    List<Integer> getChallengeIdsByRecordId(@Parameter(name = "회원 ID") @PathVariable(name = "memberId") String memberId,
                                                   @Parameter(name = "운동 기록 ID") @PathVariable(name = "recordId") Long recordId);

}
