package com.gaenari.backend.domain.member.dto;

import com.gaenari.backend.domain.member.dto.requestDto.MyPetDto;
import com.gaenari.backend.domain.member.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String birthday;
    private Gender gender;
    private int height;
    private int weight;
    private int coin;
    private LocalDateTime lastTime;
    private MyPetDto myPetDto;
}
