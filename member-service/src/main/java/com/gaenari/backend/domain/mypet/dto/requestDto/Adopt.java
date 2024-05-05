package com.gaenari.backend.domain.mypet.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Adopt {
    @Schema(description = "펫 종류", example = "1")
    private Long id;

    @Schema(description = "펫 이름", example = "초코")
    private String name;
}
