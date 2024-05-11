package com.gaenari.backend.domain.member.dto.requestDto;

import com.gaenari.backend.domain.coin.entity.CoinTitle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoinIncrease {

    @Schema(description = "증가/감소", example = "true")
    private Boolean isIncreased;

    @Schema(description = "항목", example = "MISSION")
    private CoinTitle coinTitle;

    @Schema(description = "코인량", example = "200")
    private int coinAmount;
}
