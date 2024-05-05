package com.gaenari.backend.domain.member.dto.requestDto;

import com.gaenari.backend.domain.mypet.entity.Tier;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPetDto {
    private Long id;
    private String name;
    private int affection;
    private Tier tier;
    private String changeTime;
}
