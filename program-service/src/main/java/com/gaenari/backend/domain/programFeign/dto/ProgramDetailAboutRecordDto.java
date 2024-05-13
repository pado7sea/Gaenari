package com.gaenari.backend.domain.programFeign.dto;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramTypeInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDetailAboutRecordDto {
    @Schema(description = "운동 프로그램 ID")
    private Long programId;

    @Schema(description = "운동 프로그램 제목")
    private String programTitle;

    @Schema(description = "즐겨찾기 여부")
    private Boolean isFavorite;

    @Schema(description = "운동 프로그램 타입", allowableValues = {"D", "T", "I"})
    private ProgramType type;

    @Schema(description = "운동 프로그램 정보")
    private ProgramTypeInfoDto program;

    @Schema(description = "운동 프로그램 총 사용 횟수")
    private Integer usageCount;
}
