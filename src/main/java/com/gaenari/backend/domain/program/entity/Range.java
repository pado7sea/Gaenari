package com.gaenari.backend.domain.program.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Range {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "range_id" )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @Setter
    @Column(name = "is_running")
    private boolean isRunning;

    @Setter
    @Column(name = "range_time")
    private int rangeTime;

    @Setter
    @Column(name = "range_speed")
    private int rangeSpeed;
}
