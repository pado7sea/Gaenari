package com.gaenari.backend.domain.record.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Moment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moment_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Record record;

//    @Column(name = "moment_sec")
//    private Long sec;

    @Column(name = "moment_heartrate")
    private Integer heartrate; // 분당 심박수

    @Column(name = "moment_distance")
    private Double distance; // 분당 거리

    @Column(name = "moment_pace")
    private Double pace; // 분당 페이스

/*
    보류
    @Column(name = "moment_latitude")
    private Double latitude;

    @Column(name = "moment_longitude")
    private Double longitude;
*/

    public void updateRecord(Record record) {
        this.record = record;
    }
}
