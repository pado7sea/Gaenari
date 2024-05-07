package com.gaenari.backend.domain.memberChallenge.controller;

import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberMissionDto;
import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberTrophyDto;
import com.gaenari.backend.domain.memberChallenge.service.MemberChallengeService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Member Challenge Controller", description = "Member Challenge Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/achieve")
public class MemberChallengeController {

    private final ApiResponse response;
    private final MemberChallengeService memberChallengeService;

    @Operation(summary = "회원 업적 조회", description = "회원 업적 조회")
    @GetMapping("/trophy")
    public ResponseEntity<?> getMemberTrophies(@Parameter(description = "회원 식별자 아이디") @RequestHeader("User-Info") String memberId) {
        List<MemberTrophyDto> trophyDtos = memberChallengeService.getMemberTrophies(memberId);

        return response.success(ResponseCode.ACHIEVED_TROPHY_FETCHED, trophyDtos);
    }

    @Operation(summary = "회원 미션 조회", description = "회원 미션 조회")
    @GetMapping("/mission")
    public ResponseEntity<?> getMemberMissions(@Parameter(description = "회원 식별자 아이디") @RequestHeader("User-Info") String memberId) {
        List<MemberMissionDto> missionDtos = memberChallengeService.getMemberMissions(memberId);

        return response.success(ResponseCode.ACHIEVED_MISSION_FETCHED, missionDtos);
    }

}
