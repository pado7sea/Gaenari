package com.gaenari.backend.domain.inventory.dto.responseDto;

import com.gaenari.backend.domain.item.dto.responseDto.Items;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyInventoryItems {
    private int setId;
    private int itemCnt;
    private List<Items> items;
}
