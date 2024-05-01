package com.gaenari.backend.domain.mypet.dto.responseDto;

import com.gaenari.backend.domain.mypet.entity.Tier;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendPet {
    private Long id;
    private String name;
    private Tier tier;
}
