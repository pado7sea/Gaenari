package com.gaenari.backend.domain.client.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCoinDto {
    @Schema(description = "회원 이메일")
    private String memberEmail;

    @Schema(description = "코인 수량")
    private int coinAmount;

    @Schema(description = "코인 증가 여부")
    private Boolean isIncreased;

    @Schema(description = "코인 제목", allowableValues = {"REWARD", "PETCARE", "PET_PURCHASE", "ITEM_PURCHASE"})
    private CoinTitle coinTitle;
}
