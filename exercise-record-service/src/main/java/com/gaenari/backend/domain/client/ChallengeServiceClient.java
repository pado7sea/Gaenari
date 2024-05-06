package com.gaenari.backend.domain.client;

import com.gaenari.backend.domain.client.dto.ChallengeDto;
import com.gaenari.backend.domain.client.dto.RecordAboutChallengeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "challenge-service")
public interface ChallengeServiceClient {

    @PostMapping("/achieve/feign/challengeIds")
    List<Integer> getNewlyAchievedChallengeIds(@RequestBody RecordAboutChallengeDto recordDto);

    @PostMapping("/achieve/feign/challenges")
    List<ChallengeDto> getChallenges(@RequestBody List<Integer> challengeIds);

}
