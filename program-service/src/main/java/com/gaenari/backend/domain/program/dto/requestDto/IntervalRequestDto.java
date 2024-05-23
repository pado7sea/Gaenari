package com.gaenari.backend.domain.program.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntervalRequestDto {
    @Schema(description = "인터벌 총 소요 시간", example = "360.0")
    private Double duration;

    @Schema(description = "세트 수", example = "1")
    private Integer setCount;

    @Schema(description = "세트 당 구간 수", example = "1")
    private Integer rangeCount;

    @Schema(description = "구간 정보 리스트", type = "array")
    private List<RangeRequestDto> ranges;
}
