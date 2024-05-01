package com.gaenari.backend.domain.mate.entity;

import com.gaenari.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mate")
public class Mate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mate_id")
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "friend1_id")
    @NotNull
    private Member friend1;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "friend2_id")
    @NotNull
    private Member friend2;

    @Column(name = "mate_state")
    @NotNull
    @Enumerated(EnumType.STRING)
    private State state;

}
