package com.gaenari.backend.domain.client;

import com.gaenari.backend.domain.client.dto.TotalStatisticDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exercise-record-service")
public interface RecordServiceClient {

    // exercise-record-service에서 누적 기록 가져오기
    @GetMapping("/statistic/feign/{memberId}")
    TotalStatisticDto getAllStatistics(@PathVariable(name = "memberId") Long memberId);

}
