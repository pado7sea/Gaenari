package com.gaenari.backend.domain.inventory.dto.responseDto;

import com.gaenari.backend.domain.item.dto.responseDto.Items;
import com.gaenari.backend.domain.item.dto.responseDto.Pets;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyInventoryPets {
    private Boolean isHave;
    private Pets pets;
}
