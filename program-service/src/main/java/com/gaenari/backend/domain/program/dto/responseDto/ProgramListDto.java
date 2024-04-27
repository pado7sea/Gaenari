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

    public ProgramListDto(Long id, String title, Boolean isFavorite, ProgramType type, ProgramDto.ProgramInfo programInfo, int usageCount) {
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProgramDto {
        private Long programId;
        private String programTitle;
        private Boolean isFavorite; // 즐겨찾기 등록여부
        private int usageCount; // 운동 프로그램 총 사용횟수
        private int finishedCount; // 운동 프로그램 완주 횟수

        private ProgramType type; // enum: D(거리목표), T(시간목표), I(인터벌)
        private ProgramInfo program;

        public interface ProgramInfo {}

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DistanceTargetProgramInfo implements ProgramInfo {
            private int targetValue; // 거리 목표
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TimeTargetProgramInfo implements ProgramInfo {
            private int targetValue; // 시간 목표
        }
        @Getter
        @Setter
        @NoArgsConstructor
        public static class IntervalProgramInfo implements ProgramInfo {
            private IntervalInfo intervalInfo;

            public IntervalProgramInfo(int duration, int setCount, int rangeCount, List<IntervalInfo.IntervalRange> ranges) {
                this.intervalInfo = new IntervalInfo(duration, setCount, rangeCount, ranges);
            }

        }
    }
}
