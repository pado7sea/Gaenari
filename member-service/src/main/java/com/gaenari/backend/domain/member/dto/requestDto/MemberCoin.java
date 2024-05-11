package com.gaenari.backend.domain.member.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoin {

    @Schema(description = "회원 이메일", example = "ssafy123")
    private String memberEmail;

    @Schema(description = "코인", example = "200")
    private int coin;

    @Schema(description = "증가/감소", example = "true")
    private Boolean isIncreased;
}
