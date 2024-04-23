package com.gaenari.backend.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member", indexes = {
        @Index(name = "unique_index_email", columnList = "member_email"),
        @Index(name = "unique_index_nickname", columnList = "member_nickname")
})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long Id;

    @Column(name = "member_email", length = 50, updatable = false, nullable = false, unique = true)
    private String email;

    @Column(name = "member_password", length = 50, nullable = false)
    private String password;

    @Column(name = "member_nickname", length = 10, nullable = false, unique = true)
    private String nickname;

    @Column(name = "member_birthday", length = 8, nullable = false )
    private String birthday;

    @Column(name = "member_gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "member_height", nullable = false)
    private int height;

    @Column(name = "member_weight", nullable = false)
    private int weight;

    @Builder.Default()
    @Column(name = "member_coin", nullable = false)
    private int coin = 0;

    @Column(name = "member_device")
    private String device;

    @Column(name = "member_lastTime", length = 30)
    private String lastTime;


    /* 양방향 매핑 */
//    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}) // 멤버 간 프로그램 공유 가능하게 할 것?? 일단 멤버 삭제 되더라도 멤버가 만든 프로그램은 삭제 ㄴㄴ
//    private List<Program> programList = new ArrayList<>();

}
