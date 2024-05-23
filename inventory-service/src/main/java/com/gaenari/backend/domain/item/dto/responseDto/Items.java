package com.gaenari.backend.domain.item.dto.responseDto;

import com.gaenari.backend.domain.item.entity.Category;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Items {
    private Long id;
    private Category category;
    private Boolean isEquip;
    private Boolean isHave;
}
