package com.gaenari.backend.domain.challenge.entity;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge {

    @Id
    @Column(name = "challenge_id")
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "challege_category")
    private ChallengeCategory category;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "challenge_type")
    private ChallengeType type;

    @NotNull
    @Column(name = "challenge_value")
    private Integer value;

    @NotNull
    @Column(name = "challenge_coin")
    private Integer coin;

    @Column(name = "challenge_heart")
    private Integer heart;

}
