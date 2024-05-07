package com.gaenari.backend.domain.program.dto.responseDto;

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
