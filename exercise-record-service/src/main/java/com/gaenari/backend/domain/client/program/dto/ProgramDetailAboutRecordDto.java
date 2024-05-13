package com.gaenari.backend.domain.client.program.dto;

import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDetailAboutRecordDto {
    @Schema(description = "프로그램 ID", example = "1")
    private Long programId;

    @Schema(description = "프로그램 타입", example = "D")
    private ProgramType type;

    @Schema(description = "프로그램 정보")
    private ProgramTypeInfoDto program;

    @Schema(description = "운동 프로그램 총 사용횟수", example = "10")
    private Integer usageCount;

}
