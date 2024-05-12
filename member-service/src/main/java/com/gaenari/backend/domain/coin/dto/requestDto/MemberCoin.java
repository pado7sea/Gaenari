package com.gaenari.backend.domain.coin.dto.requestDto;

import com.gaenari.backend.domain.coin.entity.CoinTitle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoin {

    @Schema(description = "회원 이메일", example = "ssafy123")
    private String memberEmail;

    @Schema(description = "증가/감소", example = "true")
    private Boolean isIncreased;

    @Schema(description = "항목", example = "MISSION")
    private CoinTitle coinTitle;

    @Schema(description = "코인량", example = "200")
    private int coinAmount;
}
