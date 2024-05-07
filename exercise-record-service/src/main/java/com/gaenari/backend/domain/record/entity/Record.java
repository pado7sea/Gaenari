package com.gaenari.backend.domain.record.entity;

import com.gaenari.backend.domain.record.dto.enumType.ExerciseType;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import com.gaenari.backend.domain.recordChallenge.entity.RecordChallenge;
import jakarta.persistence.*;
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
public class Record {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    @NotNull
    @Column(name = "member_id")
    private String memberId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "record_exercise_type")
    private ExerciseType exerciseType; // enum: W(걷기), R(달리기), P(운동 프로그램)

    @Enumerated(EnumType.STRING)
    @Column(name = "program_type")
    private ProgramType programType; // W, R인 경우에는 기본값

    @Column(name="program_id")
    private Long programId;

    @NotNull
    @Column(name="record_date")
    private LocalDateTime date;

    @NotNull
    @Column(name="record_time")
    private Double time;

    @NotNull
    @Column(name="record_distance")
    private Double distance;

    @Column(name="record_average_pace")
    private Double averagePace;

    @Column(name = "record_average_heartrate")
    private Double averageHeartRate;

    @Column(name = "record_cal")
    private Double cal;

    @NotNull
    @Column(name = "is_finished")
    private Boolean isFinished;

    /* Record - IntervalRangeRecord 양방향 매핑 */
    @Builder.Default
    @OneToMany(mappedBy = "record", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<IntervalRangeRecord> ranges = new ArrayList<>();

    /* Record - Moment 양방향 매핑 */
    @Builder.Default
    @OneToMany(mappedBy = "record", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Moment> moments = new ArrayList<>();

    /* Record - RecordChallenge 양방향 매핑 */
    @Builder.Default
    @OneToMany(mappedBy = "record", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<RecordChallenge> recordChallenges = new ArrayList<>();

}
