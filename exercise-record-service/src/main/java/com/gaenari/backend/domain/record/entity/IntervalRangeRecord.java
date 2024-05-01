package com.gaenari.backend.domain.record.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IntervalRangeRecord {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "range_record_id")
    private Long id;

    @NotNull
    @Column(name = "member_id")
    private Long memberId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    @Builder.Default
    @Column(name = "is_running")
    private boolean isRunning = true;

    @Column(name = "range_record_time")
    private Double time;

    @Column(name = "range_record_speed")
    private Double speed;

    public void updateRecord(Record record) {
        this.record = record;
    }
}
