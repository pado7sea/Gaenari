package com.gaenari.backend.domain.client.program.dto;

import com.gaenari.backend.domain.recordDetail.dto.IntervalDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramTypeInfoDto {
    @Schema(description = "목표 값", example = "100.0")
    private Double targetValue;

    @Schema(description = "인터벌 정보")
    private IntervalDto intervalInfo;
}
