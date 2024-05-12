package com.gaenari.backend.domain.statistic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statistic_id")
    private Long id;

    @NotNull
    @Column(name = "member_id")
    private String memberId;

    @NotNull
    @Builder.Default
    @Column(name = "statistic_dist")
    private Double dist = 0.0;

    @NotNull
    @Builder.Default
    @Column(name = "statistic_time")
    private Double time = 0.0;

    @NotNull
    @Builder.Default
    @Column(name = "statistic_cal")
    private Double cal = 0.0;

    @NotNull
    @Builder.Default
    @Column(name = "statistic_pace")
    private Double pace = 0.0;

    @Column(name = "statistic_date")
    private LocalDateTime date;

    @Builder.Default
    @Column(name = "statistic_count")
    private int count = 0;

    public void setDist(double v) {
        this.dist = v;
    }

    public void setTime(double v) {
        this.time = v;
    }

    public void setCal(double v) { this.cal = v; }

    public void setPace(double newAveragePace) {
        this.pace = newAveragePace;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
