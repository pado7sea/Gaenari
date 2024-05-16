package com.gaenari.backend.domain.reward.controller;

import com.gaenari.backend.domain.reward.dto.RewardDto;
import com.gaenari.backend.domain.reward.service.RewardService;
import com.gaenari.backend.global.format.code.ErrorCode;
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

@Tag(name = "Reward Controller", description = "Reward Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reward")
public class RewardController {

    private final ApiResponseCustom response;
    private final RewardService rewardService;

    @Operation(summary = "받지 않은 보상 여부", description = "받지 않은 보상이 있을 때 true")
    @GetMapping("/notice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "받지 않은 보상 여부 조회 성공", content = @Content(schema = @Schema(implementation = boolean.class)))
    })
    public ResponseEntity<?> noticeReward(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId) {
        // 회원 ID로 보상을 받지 않은 도전과제가 있는지 찾기
        boolean isExist = rewardService.findObtainableChallenge(accountId);
        return response.success(ResponseCode.REWARD_EXIST_SUCCESS, isExist);
    }

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

    @Operation(summary = "모든 보상 받기", description = "해당 회원이 받지 않은 모든 보상 받기")
    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 보상 받기 성공", content = @Content(schema = @Schema(implementation = RewardDto.class)))
    })
    public ResponseEntity<?> receiveAllRewards(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId) {
        // 회원 ID로 도전과제 ID 리스트 찾기
        List<Integer> challengeIds = rewardService.getChallengeIdsByAccountId(accountId);

        Integer coin = 0;
        Integer heart = 0;

        // 도전 과제 ID로 받을 수 있는 보상 찾기
        for (Integer challengeId : challengeIds) {
            RewardDto rewardDto = rewardService.getAttainableRewardAndReset(accountId, challengeId);
            if (rewardDto != null) {
                coin += rewardDto.getCoin();
                heart += rewardDto.getHeart();
            }
        }

        // 마이크로 서비스 간 통신을 통해서 코인 및 애정도 보상 받게 하기
        rewardService.callFeignUpdateCoin(accountId, coin);
        rewardService.callFeignUpdateHeart(accountId, heart);

        if (coin == 0 && heart == 0) {
            return response.error(ErrorCode.REWARD_NOT_FOUND);
        } else {
            // 증가한 코인과 애정도를 반환
            return response.success(ResponseCode.REWARD_RECORD_RECEIVE_SUCCESS, RewardDto.builder().accountId(accountId).coin(coin).heart(heart).build());
        }
    }

    @Operation(summary = "해당 운동 기록 관련 보상 받기", description = "운동 기록 하나로 달성한 보상 받기")
    @PutMapping("/record/{recordId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "해당 운동 기록 관련 보상 받기 성공", content = @Content(schema = @Schema(implementation = RewardDto.class)))
    })
    public ResponseEntity<?> receiveRecordRewards(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                                  @Parameter(description = "운동 기록 아이디") @PathVariable Long recordId) {
        // 운동 기록 ID로 운동 기록에 연결되어 있는 도전과제 ID 리스트 찾기
        List<Integer> challengeIds = rewardService.getChallengeIdsByRecordId(accountId, recordId);

        Integer coin = 0;
        Integer heart = 0;

        // 도전 과제 ID로 받을 수 있는 보상 찾기
        for (Integer challengeId : challengeIds) {
            RewardDto rewardDto = rewardService.getAttainableRewardAndUpdate(accountId, challengeId);
            if (rewardDto != null) {
                coin += rewardDto.getCoin();
                heart += rewardDto.getHeart();
            }
        }

        // 마이크로 서비스 간 통신을 통해서 코인 및 애정도 보상 받게 하기
        rewardService.callFeignUpdateCoin(accountId, coin);
        rewardService.callFeignUpdateHeart(accountId, heart);

        if (coin == 0 && heart == 0) {
            return response.error(ErrorCode.REWARD_NOT_FOUND);
        } else {
            // 증가한 코인과 애정도를 반환
            return response.success(ResponseCode.REWARD_RECORD_RECEIVE_SUCCESS, RewardDto.builder().accountId(accountId).coin(coin).heart(heart).build());
        }
    }

    @Operation(summary = "도전과제 보상 받기", description = "개별 도전과제 보상 받기")
    @PutMapping("/challenge/{challengeId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도전과제 보상 받기 성공", content = @Content(schema = @Schema(implementation = RewardDto.class)))
    })
    public ResponseEntity<?> receiveReward(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                           @Parameter(description = "도전과제 아이디") @PathVariable Integer challengeId) {
        // 도전 과제 ID로 받을 수 있는 보상 찾기
        RewardDto rewardDto = rewardService.getAttainableRewardAndReset(accountId, challengeId);

        if (rewardDto == null) {
            return response.error(ErrorCode.REWARD_NOT_FOUND);
        } else {
            // 마이크로 서비스 간 통신을 통해서 코인 및 애정도 보상 받게 하기
            rewardService.callFeignUpdateCoin(accountId, rewardDto.getCoin());
            rewardService.callFeignUpdateHeart(accountId, rewardDto.getHeart());

            // 증가한 코인과 애정도를 반환
            return response.success(ResponseCode.REWARD_ONE_RECEIVE_SUCCESS, rewardDto);
        }
    }
}

