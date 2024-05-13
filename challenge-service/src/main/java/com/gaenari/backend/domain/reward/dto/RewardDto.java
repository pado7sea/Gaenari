package com.gaenari.backend.domain.reward.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardDto {
    @Schema(description = "회원 아이디")
    private String memberId;

    @Schema(description = "획득한 코인")
    private Integer coin;

    @Schema(description = "획득한 하트")
    private Integer heart;
}
