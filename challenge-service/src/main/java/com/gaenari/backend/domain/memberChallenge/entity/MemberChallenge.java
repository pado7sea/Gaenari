package com.gaenari.backend.domain.memberChallenge.entity;

import com.gaenari.backend.domain.challenge.entity.Challenge;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberChallenge {

    @Id
    @GeneratedValue
    @Column(name = "member_challenge_id")
    private Long id;

    @NotNull
    @Column(name = "member_id")
    private String memberId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Builder.Default
    @Column(name = "is_achieved")
    private Boolean isAchieved = false; // 업적 달성 여부

    @Builder.Default
    @Column(name = "member_challenge_count") // 미션 달성 횟수
    private Integer count = 0;

    @Builder.Default
    @Column(name = "member_challenge_obtainable_count")
    private Integer obtainable = 0; // 획득 가능한 보상 개수

    public void updateIsAchieved(boolean b) {
        this.isAchieved = b;
    }

    public void addCount(int i) {
        this.count += i;
    }

    public void updateObtainable(int i) {
        obtainable = i;
    }

}
