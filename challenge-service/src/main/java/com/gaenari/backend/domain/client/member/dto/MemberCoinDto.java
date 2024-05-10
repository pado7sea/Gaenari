package com.gaenari.backend.domain.client.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCoinDto {
    String memberEmail;
    Boolean isIncreased;
    Integer coin;
}
