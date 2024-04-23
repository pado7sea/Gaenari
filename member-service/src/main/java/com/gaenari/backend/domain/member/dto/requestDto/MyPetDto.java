package com.gaenari.backend.domain.member.dto.requestDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPetDto {
    private int id;
    private String name;
}
