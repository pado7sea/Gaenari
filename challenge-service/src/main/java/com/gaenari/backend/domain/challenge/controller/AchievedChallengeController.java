//package com.gaenari.backend.domain.challenge.controller;
//
//import com.gaenari.backend.domain.challenge.dto.responseDto.AchievedMissionDto;
//import com.gaenari.backend.domain.challenge.dto.responseDto.AchievedTrophyDto;
//import com.gaenari.backend.domain.challenge.dto.responseDto.ChallengeDto;
//import com.gaenari.backend.domain.challenge.service.AchievedChallengeService;
//import com.gaenari.backend.domain.challenge.service.ChallengeService;
//import com.gaenari.backend.global.format.code.ResponseCode;
//import com.gaenari.backend.global.format.response.ApiResponse;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@Tag(name = "Achieved Challenge Controller", description = "Achieved Challenge Controller API")
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/achieve")
//public class AchievedChallengeController {
//
//    private final ApiResponse response;
//    private final AchievedChallengeService achievedChallengeService;
//
//    @Operation(summary = "달성 업적 조회", description = "달성 업적 조회")
//    @GetMapping("/trophy")
//    public ResponseEntity<?> getAchievedTrophies() {
//        Long memberId = 1L;
//        List<AchievedTrophyDto> trophyDtos = achievedChallengeService.getAchievedTrophies(memberId);
//
//        return response.success(ResponseCode.ACHIEVED_TROPHY_FETCHED, trophyDtos);
//    }
//
//    @Operation(summary = "달성 미션 조회", description = "달성 미션 조회")
//    @GetMapping("/mission")
//    public ResponseEntity<?> getAchievedMissions() {
//        Long memberId = 1L;
//        List<AchievedMissionDto> missionDtos = achievedChallengeService.getAchievedMissions(memberId);
//
//        return response.success(ResponseCode.ACHIEVED_MISSION_FETCHED, missionDtos);
//    }
//
//}
