package com.gaenari.backend.domain.favorite.dto.responseDto;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramTypeInfoDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto {
    private Long programId;
    private String programTitle;
    private int usageCount; // 운동 프로그램 총 사용횟수
    private int finishedCount; // 운동 프로그램 완주 횟수
    private ProgramType type; // enum: D(거리목표), T(시간목표), I(인터벌)
    private ProgramTypeInfoDto program;
}

