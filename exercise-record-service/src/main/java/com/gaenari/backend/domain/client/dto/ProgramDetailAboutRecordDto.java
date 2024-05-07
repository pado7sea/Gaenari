package com.gaenari.backend.domain.client.dto;

import com.gaenari.backend.domain.program.dto.ProgramTypeInfoDto;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDetailAboutRecordDto {
    private Long programId;

    private ProgramType type;
    private ProgramTypeInfoDto program;

    private Integer usageCount; // 운동 프로그램 총 사용횟수

}
