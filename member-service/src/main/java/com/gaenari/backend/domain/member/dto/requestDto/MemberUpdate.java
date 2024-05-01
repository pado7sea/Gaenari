package com.gaenari.backend.domain.member.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdate {

    @Schema(description = "신장", example = "160")
    private int height;

    @Schema(description = "체중", example = "60")
    private int weight;
}
