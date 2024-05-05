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
    private Double targetValue;

    @Column(name = "program_set_count")
    private Integer setCount;

    @Column(name = "program_duration")
    private Double duration;

    @NotNull
    @Column(name = "is_favorite")
    private Boolean isFavorite = false;

    @NotNull
    @Builder.Default
    @Column(name = "program_usage_count")
    private Integer usageCount = 0;

    @NotNull
    @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public void updateIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    /* Program - Range 양방향 매핑 */
    @Builder.Default
    @OneToMany(mappedBy = "program", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<IntervalRange> ranges = new ArrayList<>();

    public Integer updateUsageCount(int i) {
       return this.usageCount += i;
    }
}

