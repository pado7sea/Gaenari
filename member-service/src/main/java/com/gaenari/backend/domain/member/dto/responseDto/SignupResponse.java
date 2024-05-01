package com.gaenari.backend.domain.member.dto.responseDto;

import com.gaenari.backend.domain.member.dto.requestDto.MyPetDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse {
    private Long memberId;
    private String nickName;
    private MyPetDto myPet;
}
