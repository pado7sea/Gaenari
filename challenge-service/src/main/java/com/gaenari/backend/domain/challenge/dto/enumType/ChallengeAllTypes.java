package com.gaenari.backend.domain.challenge.dto.enumType;

import lombok.Getter;

import static com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType.D;
import static com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType.T;

@Getter
public enum ChallengeAllTypes {

    /* 미션 */
    M_DISTANCE_1KM(D, 1, 100, 2),
    M_DISTANCE_3KM(D, 3, 200, 3),
    M_DISTANCE_5KM(D, 5, 500, 5),
    M_DISTANCE_10KM(D, 10, 700, 10),

    M_TIME_10M(T, 10 * 60, 100, 2),
    M_TIME_30M(T, 30 * 60, 200, 3),
    M_TIME_60M(T, 60 * 60, 500, 5),
    M_TIME_100M(T, 100 * 60, 7000, 10),

    /* 업적 */
    T_DISTANCE_1KM(D, 1, 2000, 0),
    T_DISTANCE_4KM(D, 4, 5000, 0),
    T_DISTANCE_16KM(D, 16, 7000, 0),
    T_DISTANCE_64KM(D, 64, 10000, 0),
    T_DISTANCE_256KM(D, 256, 15000, 0),
    T_DISTANCE_1024KM(D, 1024, 20000, 0),

    T_TIME_1H(T, 60 * 60, 2000, 0),
    T_TIME_4H(T, 4 * 60 * 60, 5000, 0),
    T_TIME_16H(T, 16 * 60 * 60, 7000, 0),
    T_TIME_64H(T, 64 * 60 * 60, 10000, 0),
    T_TIME_256H(T, 256 * 60 * 60, 15000, 0),
    T_TIME_1024H(T, 1024 * 60 * 60, 200000, 0);

    private final ChallengeType type;
    private final Integer value;
    private final Integer coin;
    private final Integer heart;

    ChallengeAllTypes(ChallengeType type, Integer value, Integer coin, Integer heart) {
        this.type = type;
        this.value = value;
        this.coin = coin;
        this.heart = heart;
    }

}
