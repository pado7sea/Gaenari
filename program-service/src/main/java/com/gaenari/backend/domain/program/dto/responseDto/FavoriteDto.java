package com.gaenari.backend.domain.program.dto.responseDto;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto {
    @Schema(description = "운동 프로그램 ID", example = "1")
    private Long programId;

    @Schema(description = "운동 프로그램 제목", example = "매일 걷기")
    private String programTitle;

    @Schema(description = "운동 프로그램 총 사용 횟수", example = "10")
    private int usageCount;

    @Schema(description = "운동 프로그램 완주 횟수", example = "5")
    private int finishedCount;

    @Schema(description = "운동 프로그램 타입", allowableValues = {"D", "T", "I"})
    private ProgramType type;

    @Schema(description = "운동 프로그램 정보")
    private ProgramTypeInfoDto program;
}

