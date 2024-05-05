package com.gaenari.backend.domain.challengeFeign.dto;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionDto {
    private Integer id;             // challengeId
    private ChallengeType type;     // enum: D(거리), T(시간)
    private Integer value;
    private Integer coin;
    private Integer heart;
}
