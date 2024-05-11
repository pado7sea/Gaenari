package com.gaenari.backend.domain.member.dto.responseDto;

import com.gaenari.backend.domain.member.dto.requestDto.MyPetDto;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupResponse {
    private Long memberId;
    private String nickName;
    private MyPetDto myPet;
}
