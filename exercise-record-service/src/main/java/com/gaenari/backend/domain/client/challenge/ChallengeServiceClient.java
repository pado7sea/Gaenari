package com.gaenari.backend.domain.client.challenge;

import com.gaenari.backend.domain.client.challenge.dto.ChallengeDto;
import com.gaenari.backend.domain.client.challenge.dto.RecordAboutChallengeDto;
import com.gaenari.backend.global.format.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "challenge-service", url = "${feign.challenge-service.url}")
public interface ChallengeServiceClient {

    // 운동 기록을 보내고 새로 달성한 도전과제 ID 리스트 받아오기
    @PostMapping("/achieve/feign/challengeIds")
    ResponseEntity<GenericResponse<List<Integer>>> getNewlyAchievedChallengeIds(@RequestBody RecordAboutChallengeDto recordDto);

    // 도전과제 ID 리스트를 보내고 도전과제 리스트로 받기
    @PostMapping("/achieve/feign/challenges")
    ResponseEntity<GenericResponse<List<ChallengeDto>>> getChallenges(@RequestBody List<Integer> challengeIds);

}
