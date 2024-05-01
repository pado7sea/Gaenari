package com.gaenari.backend.domain.mate.dto.responseDto;

import com.gaenari.backend.domain.mypet.entity.Tier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ApplyMate {
    private Long mateId;      // 친구목록 아이디
    private Long memberId;    // 친구 아이디
    private String nickname;  // 친구 닉네임
    private String email;     // 친구 이메일
    private String petname;   // 친구 펫이름
    private Long breed;        // 친구 펫 품종
    private Tier tier;      // 친구 펫 티어
}
