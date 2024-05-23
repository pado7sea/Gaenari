package com.gaenari.backend.domain.member.entity;

import com.gaenari.backend.domain.coin.entity.Coin;
import com.gaenari.backend.domain.mate.entity.Mate;
import com.gaenari.backend.domain.mypet.entity.MyPet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member", indexes = {
        @Index(name = "unique_index_account_id", columnList = "member_account_id"),
        @Index(name = "unique_index_nickname", columnList = "member_nickname")
})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long Id;

    @Column(name = "member_account_id", length = 50, updatable = false, unique = true)
    @NotNull
    private String accountId;

    @Column(name = "member_password")
    @NotNull
    private String password;

    @Column(name = "member_nickname", length = 10, unique = true)
    @NotBlank // NULL, "", " " 모두 허용 안됨
    private String nickname;

    @Column(name = "member_birthday", length = 10)
    @NotNull
    private String birthday;

    @Column(name = "member_gender")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "member_height")
    @NotNull
    private int height;

    @Column(name = "member_weight")
    @NotNull
    private int weight;

    @Builder.Default()
    @Column(name = "member_coin")
    @NotNull
    private int coin = 0;

    @Column(name = "member_device")
    private String device;

    @Column(name = "member_device_time")
    private LocalDateTime deviceTime;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<MyPet> myPetList = new ArrayList<>();

    @OneToMany(mappedBy = "friend1", cascade = CascadeType.REMOVE)
    private List<Mate> mateListAsFriend1 = new ArrayList<>();

    @OneToMany(mappedBy = "friend2", cascade = CascadeType.REMOVE)
    private List<Mate> mateListAsFriend2 = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Coin> coinList = new ArrayList<>();


}
