package com.gaenari.backend.domain.recordDetail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntervalDto {
    @Schema(description = "인터벌 총 소요 시간", example = "150.0", type = "double")
    private Double duration;

    @Schema(description = "세트 수", example = "5", type = "integer")
    private Integer setCount;

    @Schema(description = "세트 당 구간 수", example = "3", type = "integer")
    private Integer rangeCount;

    @Schema(description = "구간 리스트", type = "array", implementation = RangeDto.class)
    private List<RangeDto> ranges;
}
