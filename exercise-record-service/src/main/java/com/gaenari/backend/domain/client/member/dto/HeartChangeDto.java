package com.gaenari.backend.domain.client.member.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartChangeDto {
    String accountId;
    Boolean isIncreased;
    Integer heart;
}
