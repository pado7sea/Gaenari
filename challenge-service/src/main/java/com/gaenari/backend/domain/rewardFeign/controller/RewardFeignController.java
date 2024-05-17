package com.gaenari.backend.domain.rewardFeign.controller;

import com.gaenari.backend.domain.reward.dto.RewardDto;
import com.gaenari.backend.domain.reward.service.RewardService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Reward Feign Controller", description = "Reward Feign Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reward")
public class RewardFeignController {

    private final ApiResponseCustom response;
    private final RewardService rewardService;

    @Operation(summary = "[Feign] 받지 않은 보상 여부", description = "받지 않은 보상이 있을 때 true")
    @GetMapping("/notice/{accountId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "[Feign] 받지 않은 보상 여부 조회 성공", content = @Content(schema = @Schema(implementation = boolean.class)))
    })
    public ResponseEntity<?> feignNoticeReward(@PathVariable String accountId) {
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return ResponseEntity.notFound().build();
        }

        // 회원 ID로 보상을 받지 않은 도전과제가 있는지 찾기
        boolean isExist = rewardService.findObtainableChallenge(accountId);
        return response.success(ResponseCode.REWARD_EXIST_SUCCESS, isExist);
    }

    @Operation(summary = "[Feign] 해당 운동 기록 관련 받을 수 있는 코인, 애정도 조회", description = "운동 기록 하나로 달성한 보상 조회")
    @GetMapping("/feign/{accountId}/record/{recordId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "[Feign] 해당 운동 기록 관련 받을 수 있는 코인, 애정도 조회", content = @Content(schema = @Schema(implementation = RewardDto.class)))
    })
    public ResponseEntity<?> getAttainableRewards(@Parameter(description = "회원 ID") @PathVariable String accountId, @Parameter(description = "운동 기록 ID") @PathVariable Long recordId) {
        // 운동 기록 ID로 운동 기록에 연결되어 있는 도전과제 ID 리스트 찾기
        List<Integer> challengeIds = rewardService.getChallengeIdsByRecordId(accountId, recordId);

        Integer coin = 0;
        Integer heart = 0;

        // 도전 과제 ID로 받을 수 있는 보상 찾기
        for (Integer challengeId : challengeIds) {
            RewardDto rewardDto = rewardService.getAttainableReward(accountId, challengeId);
            if (rewardDto != null) {
                coin += rewardDto.getCoin();
                heart += rewardDto.getHeart();
            }
        }

        // 받을 수 있는 코인과 애정도를 반환
        return response.success(ResponseCode.REWARD_RECORD_RECEIVE_SUCCESS, RewardDto.builder().accountId(accountId).coin(coin).heart(heart).build());
    }

}

