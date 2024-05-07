package com.gaenari.backend.domain.recordDetail.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramInfoDto {
    private Long programId;
    private Double targetValue;
    private IntervalDto intervalInfo;
}
