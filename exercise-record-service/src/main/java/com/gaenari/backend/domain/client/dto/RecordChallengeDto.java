package com.gaenari.backend.domain.client.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordChallengeDto {
    private Integer challengeId;
    private Boolean isObtained = false;
}
