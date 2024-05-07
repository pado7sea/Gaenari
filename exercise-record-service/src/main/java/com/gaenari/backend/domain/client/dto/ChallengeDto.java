package com.gaenari.backend.domain.client.dto;

import com.gaenari.backend.domain.client.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.record.dto.enumType.ChallengeType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDto {
    private int id;
    private ChallengeCategory category;
    private ChallengeType type;
    private Integer value;
    private Integer coin;
    private Integer heart;
}
