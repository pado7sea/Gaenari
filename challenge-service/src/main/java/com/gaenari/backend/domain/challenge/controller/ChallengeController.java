package com.gaenari.backend.domain.challenge.controller;

import com.gaenari.backend.domain.challenge.dto.responseDto.ChallengeDto;
import com.gaenari.backend.domain.challenge.service.ChallengeService;
import com.gaenari.backend.global.format.code.ErrorCode;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Challenge Controller", description = "Challenge Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge")
public class ChallengeController {

    private final ApiResponse response;
    private final ChallengeService challengeService;

    @Operation(summary = "도전 과제 생성", description = "이미 생성된 도전과제가 있으면 생성되지 않습니다.")
    @PostMapping("/create")
    public ResponseEntity<?> createChallenge() {
        boolean success = challengeService.createChallenge();

        if (success) {
            return response.success(ResponseCode.CHALLENGE_CREATED);
        } else {
            return response.error(ErrorCode.CHALLENGE_CREATE_FAILED);
        }
    }

    @Operation(summary = "도전 과제 조회", description = "도전 과제 조회")
    @GetMapping
    public ResponseEntity<?> getAllChallenges() {
        List<ChallengeDto> challengeDtos = challengeService.getAllChallenges();

        return response.success(ResponseCode.CHALLENGE_FETCHED, challengeDtos);
    }

}
