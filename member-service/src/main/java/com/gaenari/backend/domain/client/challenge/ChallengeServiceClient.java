package com.gaenari.backend.domain.client.challenge;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "challenge-service", url = "${feign.challenge-service.url}")
public interface ChallengeServiceClient {
    @GetMapping("/reward/notice/{memberId}") // 받지 않은 보상 여부
    boolean isGetReward(@PathVariable String memberId);
}
