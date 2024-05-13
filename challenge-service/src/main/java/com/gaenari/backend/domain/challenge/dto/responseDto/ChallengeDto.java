package com.gaenari.backend.domain.challenge.dto.responseDto;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDto {
    @Schema(description = "도전 과제 ID", example = "1")
    private int id;

    @Schema(description = "도전 카테고리", type = "string")
    private ChallengeCategory category;

    @Schema(description = "도전 타입", type = "string")
    private ChallengeType type;

    @Schema(description = "도전 값", example = "10")
    private Integer value;

    @Schema(description = "획득 코인", example = "100")
    private Integer coin;

    @Schema(description = "획득 하트", example = "50")
    private Integer heart;
}
