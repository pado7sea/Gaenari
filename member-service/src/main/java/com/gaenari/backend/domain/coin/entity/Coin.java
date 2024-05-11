package com.gaenari.backend.domain.coin.entity;

import com.gaenari.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "coin")
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coin_id")
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_member")
    @NotNull
    private Member member;

    @Column(name = "coin_year")
    @NotNull
    private int year;

    @Column(name = "coin_month")
    @NotNull
    private int month;

    @Column(name = "coin_day")
    @NotNull
    private int day;

    @Column(name = "coin_time")
    private LocalTime time;

    @Column(name = "coin_is_increased")
    @NotNull
    private Boolean isIncreased;

    @Column(name = "coin_title")
    @NotNull
    @Enumerated(EnumType.STRING)
    private CoinTitle coinTitle;

    @Column(name = "coin_amount")
    @NotNull
    private int coinAmount;

    @Column(name = "coin_balance")
    @NotNull
    private int balance;
}
