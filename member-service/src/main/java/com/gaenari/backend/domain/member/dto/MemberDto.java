package com.gaenari.backend.domain.member.dto;

import com.gaenari.backend.domain.mypet.dto.MyPetDto;
import com.gaenari.backend.domain.member.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long memberId;
    private String accountId;
    private String nickname;
    private String birthday;
    private Gender gender;
    private int height;
    private int weight;
    private int coin;
    private MyPetDto myPetDto;
}
