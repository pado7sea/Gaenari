package com.gaenari.backend.domain.program.dto.responseDto;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramListDto {
    private List<ProgramDto> data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProgramDto {
        private Long programId;
        private String programTitle;
        private boolean isFavorite; // 즐겨찾기 등록여부

        private ProgramType type; // enum: D(거리목표), T(시간목표), I(인터벌)
        private ProgramDetail program;

        private int usageCount; // 운동 프로그램 총 사용횟수
        private int finishedCount; // 운동 프로그램 완주 횟수
        public interface ProgramDetail {}

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DistanceTargetProgramDetail implements ProgramDetail {
            private int targetValue;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class IntervalProgramDetail implements ProgramDetail {
            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Interval {
                private int duration; // 인터벌 총 소요 시간
                private int setCount; // 세트 수
                private int rangeCount; // 세트 당 구간 수
                private List<Range> ranges;

                @Getter
                @Setter
                @NoArgsConstructor
                @AllArgsConstructor
                public static class Range { // 구간
                    private int rangeId;
                    private boolean isRunning;
                    private int time;
                    private int speed;
                }

            }
        }

    }


}
