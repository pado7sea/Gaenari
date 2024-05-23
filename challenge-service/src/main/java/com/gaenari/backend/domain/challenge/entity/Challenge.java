package com.gaenari.backend.domain.challenge.entity;

import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.challenge.dto.enumType.ChallengeType;
import com.gaenari.backend.domain.memberChallenge.entity.MemberChallenge;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    /* Challenge - MemberChallenge 양방향 매핑 */
    @Builder.Default
    @OneToMany(mappedBy = "challenge", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<MemberChallenge> memberChallenges = new ArrayList<>();

}
