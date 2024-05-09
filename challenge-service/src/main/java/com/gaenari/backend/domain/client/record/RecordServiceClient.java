package com.gaenari.backend.domain.client.record;

import com.gaenari.backend.domain.client.record.dto.TotalStatisticDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exercise-record-service", url = "${feign.member-service.url}")
public interface RecordServiceClient {

    // 회원 ID를 보내고 누적 기록 가져오기
    @GetMapping("/statistic/feign/{memberId}")
    TotalStatisticDto getAllStatistics(@PathVariable(name = "memberId") String memberId);

    // 회원 ID와 recordChallenge 리스트를 반환

}
