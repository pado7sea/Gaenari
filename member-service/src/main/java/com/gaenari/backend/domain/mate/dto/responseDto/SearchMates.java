package com.gaenari.backend.domain.mate.dto.responseDto;

import com.gaenari.backend.domain.mate.entity.Call;
import com.gaenari.backend.domain.mate.entity.State;
import com.gaenari.backend.domain.mypet.dto.responseDto.FriendPet;
import com.gaenari.backend.domain.mypet.entity.Tier;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMates {
    private Long mateId;        // 친구가 아니면 -1
    private Long memberId;
    private String nickName;
    private State state;
    private Call call;          // 친구요청 수신/발신/없음
    private Long petId;
    private String petName;
    private Tier petTier;
}
