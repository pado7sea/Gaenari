package com.gaenari.backend.domain.mate.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MateCheck {
    @Schema(description = "mate_id", example = "1")
    private Long mateId;

    @Schema(description = "friend_id", example = "2")
    private Long friendId;

    @Schema(description = "수락/거절 유무", example = "true")
    private Boolean isAccept;
}
