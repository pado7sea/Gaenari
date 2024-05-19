package com.gaenari.backend.domain.memberChallenge.controller;

import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberMissionDto;
import com.gaenari.backend.domain.memberChallenge.dto.responseDto.MemberTrophyDto;
import com.gaenari.backend.domain.memberChallenge.service.MemberChallengeService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    private final ApiResponseCustom response;
    private final MemberChallengeService memberChallengeService;

    @Operation(summary = "회원 모든 업적 조회", description = "회원 업적 조회")
    @GetMapping("/trophy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 모든 업적 조회 성공", content = @Content(schema = @Schema(implementation = MemberTrophyDto.class)))
    })
    public ResponseEntity<?> getAllMemberTrophies(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId) {
        List<MemberTrophyDto> trophyDtos = memberChallengeService.getAllMemberTrophies(accountId);

        return response.success(ResponseCode.ACHIEVED_TROPHY_FETCHED, trophyDtos);
    }

    @Operation(summary = "회원 모든 미션 조회", description = "회원 미션 조회")
    @GetMapping("/mission")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 모든 미션 조회 성공", content = @Content(schema = @Schema(implementation = MemberMissionDto.class)))
    })
    public ResponseEntity<?> getAllMemberMissions(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId) {
        List<MemberMissionDto> missionDtos = memberChallengeService.getAllMemberMissions(accountId);

        return response.success(ResponseCode.ACHIEVED_MISSION_FETCHED, missionDtos);
    }

}
