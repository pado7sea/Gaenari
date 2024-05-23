package com.gaenari.backend.domain.program.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntervalDto {
    @Schema(description = "인터벌 총 소요 시간", example = "360.0")
    private Double duration;

    @Schema(description = "세트 수", example = "3")
    private Integer setCount;

    @Schema(description = "세트 당 구간 수", example = "4")
    private Integer rangeCount;

    @Schema(description = "구간 리스트")
    private List<RangeDto> ranges;
}
