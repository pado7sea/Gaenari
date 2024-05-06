package com.gaenari.backend.domain.client.dto.enumType;

import lombok.Getter;

import static com.gaenari.backend.domain.client.dto.enumType.ChallengeAllTypes.*;


@Getter
public enum ChallengeCategory {
    MISSION(new ChallengeAllTypes[]{
            M_DISTANCE_1KM, M_DISTANCE_3KM, M_DISTANCE_5KM, M_DISTANCE_10KM,
            M_TIME_10M,M_TIME_30M, M_TIME_60M, M_TIME_100M
    }),
    TROPHY(new ChallengeAllTypes[]{
            T_DISTANCE_1KM, T_DISTANCE_4KM, T_DISTANCE_16KM, T_DISTANCE_64KM, T_DISTANCE_256KM, T_DISTANCE_1024KM,
            T_TIME_1H, T_TIME_4H, T_TIME_16H, T_TIME_64H, T_TIME_256H, T_TIME_1024H
    });

    private final ChallengeAllTypes[] types;

    ChallengeCategory(ChallengeAllTypes[] types) {
        this.types = types;
    }

}
