package com.gaenari.backend.domain.client.member.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartChangeDto {
    String memberEmail;
    Boolean isIncreased;
    Integer heart;
}
