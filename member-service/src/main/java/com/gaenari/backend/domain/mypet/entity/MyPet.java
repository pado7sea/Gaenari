package com.gaenari.backend.domain.mypet.entity;

import com.gaenari.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mypet")
public class MyPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mypet_id")
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "memer_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @Column(name = "mypet_name")
    @NotNull
    private String name;

    @Column(name = "mypet_affection")
    @NotNull
    private int affection = 0;

    @Column(name = "mypet_tier")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @NotNull
    private Tier tier = Tier.BRONZE;

    @Column(name = "mypet_is_partner")
    private Boolean isPartner = false;

    @Column(name = "mypet_change_time")
    private String changeTime;
}
