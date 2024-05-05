package com.gaenari.backend.domain.challengeFeign.controller;

import com.gaenari.backend.domain.challengeFeign.dto.MissionDto;
import com.gaenari.backend.domain.challengeFeign.dto.RecordAboutChallengeDto;
import com.gaenari.backend.domain.challengeFeign.dto.TrophyDto;
import com.gaenari.backend.domain.challengeFeign.service.AchievedChallengeFeignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Achieved Challenge Feign Controller", description = "Achieved Challenge Feign Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/achieve/feign")
public class AchievedChallengeFeignController {

    private final AchievedChallengeFeignService achievedChallengeFeignService;

    @Operation(summary = "기록당 달성 과제 조회", description = "도전과제 아이디가 배열로 출력")
    @PostMapping("/challenge")
    public List<Integer> getAchievedChallengeIds(@RequestBody RecordAboutChallengeDto recordDto) {
        List<Integer> challengeIds = new ArrayList<>();
        challengeIds.addAll(achievedChallengeFeignService.getAchievedMissionIds(recordDto));
        challengeIds.addAll(achievedChallengeFeignService.getAchievedTrophyIds(recordDto));

        return challengeIds;
    }

    @Operation(summary = "업적 조회", description = "도전과제 아이디에 해당하는 업적 조회")
    @GetMapping("/trophy/{challengeId}")
    public TrophyDto getTrophy(@PathVariable(name = "challengeId") Integer challengeId) {

        return achievedChallengeFeignService.getTrophy(challengeId);
    }

    @Operation(summary = "미션 조회", description = "도전과제 아이디에 해당하는 미션 조회")
    @GetMapping("/mission/{challengeId}")
    public MissionDto getMission(@PathVariable(name = "challengeId") Integer challengeId) {

        return achievedChallengeFeignService.getMission(challengeId);
    }

}
