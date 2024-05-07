package com.gaenari.backend.domain.program.dto.responseDto;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDto {
    private Long programId;
    private String programTitle;
    private Boolean isFavorite; // 즐겨찾기 등록여부

    private Integer usageCount; // 운동 프로그램 총 사용횟수
    private Integer finishedCount; // 운동 프로그램 완주 횟수

    private ProgramType type; // enum: D(거리목표), T(시간목표), I(인터벌)
    private ProgramTypeInfoDto program;

}

