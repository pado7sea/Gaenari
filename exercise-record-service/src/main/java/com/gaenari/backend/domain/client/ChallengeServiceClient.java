package com.gaenari.backend.domain.client;

import com.gaenari.backend.domain.client.dto.RecordAboutChallengeDto;
import com.gaenari.backend.domain.recordDetail.dto.RecordDetailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "challenge-service")
public interface ChallengeServiceClient {

    @PostMapping("/achieve/feign/challenge")
    List<Integer> getAchievedChallengeIds(@RequestBody RecordAboutChallengeDto recordDto);

    @GetMapping("/achieve/feign/trophy/{challengeId}")
    RecordDetailDto.TrophyDto getTrophy(@PathVariable(name = "challegeId") Integer challengeId);

    @GetMapping("/achieve/feign/mission/{challengeId}")
    RecordDetailDto.MissionDto getMission(@PathVariable(name = "challegeId") Integer challengeId);

}
