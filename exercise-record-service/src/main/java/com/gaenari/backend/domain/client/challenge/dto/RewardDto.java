package com.gaenari.backend.domain.client.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardDto {
    @Schema(description = "회원 아이디")
    private String accountId;

    @Schema(description = "획득한 코인")
    private Integer coin;

    @Schema(description = "획득한 하트")
    private Integer heart;
}
