package com.gaenari.backend.domain.client.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartChangeDto {
    @Schema(description = "회원 이메일")
    private String memberEmail;

    @Schema(description = "하트 증가 여부")
    private Boolean isIncreased;

    @Schema(description = "변경된 하트 수")
    private Integer heart;
}
