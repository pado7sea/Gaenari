package com.gaenari.backend.domain.program.dto;

import com.gaenari.backend.domain.recordDetail.dto.IntervalDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramTypeInfoDto {
    private Double targetValue;
    private IntervalDto intervalInfo;
}
