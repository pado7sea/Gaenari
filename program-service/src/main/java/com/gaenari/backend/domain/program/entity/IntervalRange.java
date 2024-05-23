package com.gaenari.backend.domain.program.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IntervalRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "range_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @NotNull
    @Builder.Default
    @Column(name = "is_running")
    private Boolean isRunning = true;

    @NotNull
    @Column(name = "range_time")
    private Double time;

    @Column(name = "range_speed")
    private Double speed;

    public void updateProgram(Program program) {
        this.program = program;
    }
}
