package com.gaenari.backend.domain.program.dto.requestDto;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramUpdateDto {
    private Long programId;
    private String programTitle;
    private ProgramType programType; // enum: D(거리목표), T(시간목표), I(인터벌)
    private Integer programTargetValue; // 거리목표/시간목표 수치. type이 I인 경우 -1
    private IntervalRequestDto interval;
}
