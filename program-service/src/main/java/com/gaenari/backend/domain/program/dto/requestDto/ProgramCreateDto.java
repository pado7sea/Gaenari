package com.gaenari.backend.domain.program.dto.requestDto;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramCreateDto {
    @Schema(description = "운동 프로그램 제목")
    private String programTitle;

    @Schema(description = "운동 프로그램 타입", allowableValues = {"D", "T", "I"})
    private ProgramType programType;

    @Schema(description = "운동 프로그램 목표 수치. 거리목표/시간목표의 경우 해당 값이 지정. 인터벌의 경우 null")
    private Double programTargetValue;

    @Schema(description = "인터벌 프로그램 요청 정보. 거리목표/시간목표의 경우 해당 값이 null")
    private IntervalRequestDto interval;
}
