package com.gaenari.backend.domain.reward.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardDto {
    private Integer challengeId;
    private Integer coin;
    private Integer heart;
}
