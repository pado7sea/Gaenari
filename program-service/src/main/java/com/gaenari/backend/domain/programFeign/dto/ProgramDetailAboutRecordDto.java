package com.gaenari.backend.domain.programFeign.dto;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramTypeInfoDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDetailAboutRecordDto {
    private Long programId;
    private String programTitle;
    private Boolean isFavorite;

    private ProgramType type;
    private ProgramTypeInfoDto program;

    private Integer usageCount; // 운동 프로그램 총 사용횟수

}
