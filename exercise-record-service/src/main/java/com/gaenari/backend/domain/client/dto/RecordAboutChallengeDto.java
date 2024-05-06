package com.gaenari.backend.domain.client.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordAboutChallengeDto {
    private Long memberId;
    private Long recordId;
    private Double distance;    // 해당 기록 시간
    private Double time;        // 해당 기록 거리
    private Double statisticDistance; // 멤버 누적 거리
    private Double statisticTime; // 멤버 누적 시간
}
