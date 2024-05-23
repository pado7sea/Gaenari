package com.gaenari.backend.domain.mypet.dto;

import com.gaenari.backend.domain.mypet.entity.Tier;
import lombok.*;

import java.time.LocalDateTime;

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
