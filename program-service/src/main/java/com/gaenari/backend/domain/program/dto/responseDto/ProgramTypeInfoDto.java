package com.gaenari.backend.domain.program.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramTypeInfoDto {
    @Schema(description = "운동 프로그램의 목표 값", example = "10.0")
    private Double targetValue;

    @Schema(description = "운동 프로그램의 인터벌 정보")
    private IntervalDto intervalInfo;
}
