package com.gaenari.backend.domain.program.dto.responseDto;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import com.gaenari.backend.domain.program.entity.Range;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDetailDto {
    private int programId;
    private String programTitle;
    private boolean isFavorite;

    private ProgramType type;
    private ProgramDetail program;

    private TotalRecordDto totalRecord;
    private int usageCount; // 운동 프로그램 총 사용횟수
    private int finishedCount; // 운동 프로그램 완주 횟수
    private List<UsageLogDto> usageLog;


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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TotalRecordDto {
        private double distance;
        private long time;
        private int cal;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsageLogDto {
        private int exerciseId;
        private double distance;
        private int averagePace;
        private long time;
        private Date date;
        private int cal;
        private boolean isFinished;
    }

}
