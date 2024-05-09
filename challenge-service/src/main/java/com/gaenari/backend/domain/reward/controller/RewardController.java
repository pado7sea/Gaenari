package com.gaenari.backend.domain.reward.controller;

import com.gaenari.backend.domain.reward.dto.RewardDto;
import com.gaenari.backend.domain.reward.service.RewardService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member Challenge Controller", description = "Member Challenge Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reward")
public class RewardController {

    private final ApiResponse response;
    private final RewardService rewardService;

    @Operation(summary = "모든 보상 받기", description = "해당 회원이 받지 않은 모든 보상 받기 - 지금 ㄴㄴ")
    @PutMapping
    public ResponseEntity<?> receiveAllRewards(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId) {


        return response.success(ResponseCode.REWARD_RECORD_RECEIVE_SUCCESS, 0);
    }

    @Operation(summary = "해당 운동 기록 보상 받기", description = "운동 기록 하나로 달성한 보상 받기 - 지금 ㄴㄴ")
    @PutMapping("/record/{recordId}")
    public ResponseEntity<?> receiveRecordRewards(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId,
                                                  @Parameter(description = "도전과제 아이디") @PathVariable Long recordId) {


        return response.success(ResponseCode.REWARD_RECORD_RECEIVE_SUCCESS, 0);
    }

    @Transactional
    @Operation(summary = "도전과제 보상 받기", description = "개별 도전과제 보상 받기")
    @PutMapping("/challenge/{challengeId}")
    public ResponseEntity<?> receiveReward(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId,
                                           @Parameter(description = "도전과제 아이디") @PathVariable Integer challengeId) {
        // 도전 과제 아이디로 받을 수 있는 보상 찾기
        RewardDto rewardDto = rewardService.getAttainableReward(memberId, challengeId);

        // 마이크로 서비스 간 통신을 통해서 코인 및 애정도 보상 받게 하기
        rewardService.callFeignUpdateCoin(memberId, rewardDto.getCoin());
        rewardService.callFeignUpdateHeart(memberId, rewardDto.getHeart());

        return response.success(ResponseCode.REWARD_ONE_RECEIVE_SUCCESS, rewardDto);
    }

    // memberId랑 recordChallengeID 를 받을거아님? 그걸 보내
    // RC에서 RC ID 로 조회한 memberId랑 일치하는지 확인해 그러면 데이터를 보내.
    // 없으면 일단 빈 리스트로 보내.. responseentity가 아니라 에러는 못보내니까..

    // RC
    // 기록하나에딸린도전과제 조회-> challengeRecord 뒤져-> 보상 안받은 것만 가져와-> 근데 다 안받았겠지 뭐
    // 얘네 상태 다 보상 받기로 바꿔
    // 리스트도 받아와 거기서 attainable coin이랑 heart 계산해서 받아와

    // MC
    // 그리고 memberChallenge에서 보상 받은 만큼 빼야함..
    // 업적의 경우 -> 획득 가능한 보상 개수가 0개로! 걍 받은 상태로 바꿈 근데 빼기 1하면 결국 0아닌가 ㅋㅋ
    // 미션의 경우 -> 획득 가능한 보상 개수 -1

    // ----------
    // 마이크로 서비스로 기록 하나에 딸린
    // 기록 하나에 딸린 업적, 미션 순회하면서 받을 수 있는 보상 개수 * 코인 계산 , 애정도 계산
    // 그리고 받을 수 있는 보상 개수를 0으로 초기화
    // 다른 마이크로서비스로 코인, 애정도 증가 요청보내기
    // -------


    // 멤버 하나에 딸린 코인 애정도 받기
    // 이하 동문.. 비슷한 거는 공통 메서드로 빼자!


    // 보상 받기 - interservice (멤버서비스에서 여기로 api요청해야할듯)
    // 업적
    // 미션
    // 받지 않은 보상 개수를 0개로
    // 받지않은 개수 곱하기 미션의 코인, 애정도 반환
    // 멤버의 코인, 애정도 증가 (이거는 멤버서비스에서)
}
