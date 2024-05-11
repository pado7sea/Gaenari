package com.gaenari.backend.domain.inventory.dto.responseDto;

import com.gaenari.backend.domain.item.entity.Tier;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendPet {
    private Long id;
    private String name;
    private Long affection;
    private Tier tier;
}
