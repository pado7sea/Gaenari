package com.gaenari.backend.domain.mypet.dto.responseDto;

import com.gaenari.backend.domain.mypet.entity.Tier;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendPetDetail {
    private Long id;
    private String name;
    private int affection;
    private Tier tier;
    private LocalDateTime changeTime;
}
