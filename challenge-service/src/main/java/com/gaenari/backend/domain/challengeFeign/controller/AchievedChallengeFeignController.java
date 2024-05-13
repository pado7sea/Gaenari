package com.gaenari.backend.domain.challengeFeign.controller;

import com.gaenari.backend.domain.challenge.dto.responseDto.ChallengeDto;
import com.gaenari.backend.domain.challengeFeign.dto.RecordAboutChallengeDto;
import com.gaenari.backend.domain.challengeFeign.service.AchievedChallengeFeignService;
import com.gaenari.backend.domain.memberChallenge.service.MemberChallengeService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Achieved Record Challenge Feign Controller", description = "Achieved Record Challenge Feign Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/achieve/feign")
public class AchievedChallengeFeignController {

    private final ApiResponseCustom response;
    private final MemberChallengeService memberChallengeService;
    private final AchievedChallengeFeignService achievedChallengeFeignService;

    // exercise-record-service에서 기록 저장 시 사용
    @Transactional
    @Operation(summary = "[Feign] 기록당 달성 과제 조회", description = "도전과제 아이디 리스트로 출력")
    @PostMapping("/challengeIds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "[Feign] 기록당 달성 과제 조회 성공", content = @Content(schema = @Schema(implementation = Long.class)))
    })
    public ResponseEntity<?> getNewlyAchievedChallengeIds(@RequestBody RecordAboutChallengeDto recordDto) {
        
        // 해당 운동 기록을 통해 새롭게 달성한 도전과제 아이디 리스트 조회
        List<Integer> challengeIds = achievedChallengeFeignService.getNewlyAchievedChallengeIds(recordDto);

        // 도전과제 아이디 리스트로 해당 회원의 회원 운동 기록 업데이트
        challengeIds.forEach(challengeId -> memberChallengeService.updateMemberChallenge(recordDto.getMemberId(), challengeId));

        return response.success(ResponseCode.CHALLENGE_FETCHED, challengeIds);
    }

    // exercise-record-service에서 상세 기록 조회 시 사용
    @Operation(summary = "[Feign] 도전과제 리스트 조회", description = "도전과제 아이디 리스트를 도전과제 리스트로 반환")
    @PostMapping("/challenges")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "[Feign] 도전과제 리스트 조회 성공", content = @Content(schema = @Schema(implementation = ChallengeDto.class)))
    })
    public ResponseEntity<?> getChallenges(@RequestBody List<Integer> challengeIds) {
        List<ChallengeDto> challengeDtos = achievedChallengeFeignService.getChallenges(challengeIds);

        return response.success(ResponseCode.CHALLENGE_FETCHED, challengeDtos);
    }


}
