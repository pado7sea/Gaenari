package com.gaenari.backend.domain.program.entity;

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
    private String memberId;

    @Setter
    @Column(name = "program_title")
    private String title;

    @Setter
    @Column(name = "program_distance")
    private int distance;

    @Setter
    @Column(name = "program_time")
    private int time;

    @Setter
    @Column(name = "is_interval")
    private boolean isInterval;

    @Setter
    @Column(name = "program_setCount")
    private int setCount;

    @Setter
    @Column(name = "program_duration")
    private int duration;

    @Setter
    @Column(name = "is_favorite")
    private boolean isFavorite;

    @Setter
    @Column(name = "program_usageCount")
    private int usageCount;

    public static Program of(String memberId, String title, int distance, int time, boolean isInterval,
                             int setCount, int duration, boolean isFavorite, int usageCount) {
        return Program.builder()
                .memberId(memberId)
                .title(title)
                .distance(distance)
                .time(time)
                .isInterval(isInterval)
                .setCount(setCount)
                .duration(duration)
                .isFavorite(isFavorite)
                .usageCount(usageCount)
                .build();
    }

    public void updateTitle(String title) { this.title = title; }

    public void updateDistance(int distance) { this.distance = distance; }

    public void updateTime(int time) { this.time = time; }

    public void updateIsInterval(boolean isInterval) { this.isInterval = isInterval; }

    public void updateSetCount(int setCount) { this.setCount = setCount; }

    public void updateDuration(int duration) { this.duration = duration; }

    public void updateIsFavorite(boolean isFavorite) { this.isFavorite = isFavorite; }

    public void updateUsageCount(int usageCount) { this.usageCount = usageCount; }

    /* program - range 양방향 매핑 */
    @Builder.Default
    @OneToMany(mappedBy = "program", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Range> rangeList = new ArrayList<>();


}

