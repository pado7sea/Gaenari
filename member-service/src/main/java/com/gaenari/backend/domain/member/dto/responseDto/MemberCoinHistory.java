package com.gaenari.backend.domain.member.dto.responseDto;

import com.gaenari.backend.domain.member.dto.requestDto.MemberCoin;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoinHistory {
    private int year;
    private int month;
    private List<MemberCoinRecord> memberCoinRecordList;
}
