package com.gaenari.backend.domain.mypet.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IncreaseAffection {
    @Schema(description = "펫 종류", example = "1")
    private Long id;

    @Schema(description = "증가할 애정도", example = "20")
    private int affection;
}
