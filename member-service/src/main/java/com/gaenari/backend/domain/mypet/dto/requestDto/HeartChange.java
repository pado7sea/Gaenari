package com.gaenari.backend.domain.mypet.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartChange {
    @Schema(description = "회원 이메일", example = "ssafy123")
    private String memberEmail;

    @Schema(description = "증가/감소", example = "true")
    private Boolean isIncreased;

    @Schema(description = "증가할 애정도", example = "20")
    private int heart;
}
