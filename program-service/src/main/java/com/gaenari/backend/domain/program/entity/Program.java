package com.gaenari.backend.domain.program.entity;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE program SET is_deleted = TRUE WHERE program_id = ?")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long id;

    @NotNull
    @Column(name = "member_id")
    private Long memberId;

    @NotNull
    @Column(name = "program_title", length = 8)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "program_type")
    private ProgramType type;

    @Column(name = "program_target_value")
    private Integer targetValue;

    @Column(name = "program_set_count")
    private Integer setCount;

    @Column(name = "program_duration")
    private Integer duration;

    @NotNull
    @Column(name = "is_favorite")
    private boolean isFavorite = false;

    @NotNull
    @Builder.Default
    @Column(name = "program_usage_count")
    private Integer usageCount = 0;

    @NotNull
    @Builder.Default
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public static Program of(Long memberId, String title, ProgramType type, Integer targetValue,
                             Integer setCount, Integer duration, boolean isFavorite, Integer usageCount, boolean isDeleted) {
        return Program.builder()
                .memberId(memberId)
                .title(title)
                .type(type)
                .targetValue(targetValue)
                .setCount(setCount)
                .duration(duration)
                .isFavorite(isFavorite)
                .usageCount(usageCount)
                .isDeleted(isDeleted)
                .build();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateType(ProgramType type) {
        this.type = type;
    }

    public void updateTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }

    public void updateSetCount(Integer setCount) {
        this.setCount = setCount;
    }

    public void updateDuration(Integer duration) {
        this.duration = duration;
    }

    public void updateIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void updateUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    /* program - range 양방향 매핑 */
    @Builder.Default
    @OneToMany(mappedBy = "program", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<IntervalRange> ranges = new ArrayList<>();

}

