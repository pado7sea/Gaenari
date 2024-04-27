package com.gaenari.backend.domain.program.entity;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import jakarta.persistence.*;
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

    // TODO: MSA에서는 연관관계 처리를 어떻게 해야할지 알아봐야 함
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

    @Setter
    @Column(name = "member_id")
    private Long memberId;

    @Setter
    @Column(name = "program_title")
    private String title;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "program_type")
    private ProgramType type;

    @Setter
    @Column(name = "program_target_value")
    private int targetValue;

    @Setter
    @Column(name = "program_set_count")
    private int setCount;

    @Setter
    @Column(name = "program_duration")
    private int duration;

    @Setter
    @Column(name = "is_favorite")
    private boolean isFavorite;

    @Setter
    @Column(name = "program_usage_count")
    private int usageCount;

    @Setter
    @Column(name = "is_deleted")
    private boolean isDeleted;

    public static Program of(Long memberId, String title, ProgramType type, int targetValue,
                             int setCount, int duration, boolean isFavorite, int usageCount, boolean isDeleted) {
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

    public void updateTitle(String title) { this.title = title; }

    public void updateType(ProgramType type) { this.type = type; }

    public void updateTargetValue(int targetValue) { this.targetValue = targetValue; }

    public void updateSetCount(int setCount) { this.setCount = setCount; }

    public void updateDuration(int duration) { this.duration = duration; }

    public void updateIsFavorite(boolean isFavorite) { this.isFavorite = isFavorite; }

    public void updateUsageCount(int usageCount) { this.usageCount = usageCount; }

    /* program - range 양방향 매핑 */
    @Builder.Default
    @OneToMany(mappedBy = "program", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<IntervalRange> ranges = new ArrayList<>();

}

