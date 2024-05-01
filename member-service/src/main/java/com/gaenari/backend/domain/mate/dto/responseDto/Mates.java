package com.gaenari.backend.domain.mate.dto.responseDto;

import com.gaenari.backend.domain.mypet.dto.responseDto.FriendPet;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mates {
    private Long mateId;
    private Long memberId;
    private String nickName;
    private FriendPet mypet;
}
