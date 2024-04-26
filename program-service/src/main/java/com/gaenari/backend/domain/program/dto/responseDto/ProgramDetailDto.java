package com.gaenari.backend.domain.program.dto.responseDto;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
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
    private Long programId;
    private String programTitle;
    private Boolean isFavorite;

    private ProgramType type;
    private ProgramInfo program;

//    private TotalRecordDto totalRecord;
//    private int usageCount; // 운동 프로그램 총 사용횟수
//    private int finishedCount; // 운동 프로그램 완주 횟수
//    private List<UsageLogDto> usageLog;

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

        public IntervalProgramInfo(int duration, int setCount, List<IntervalInfo.IntervalRange> ranges) {
            this.intervalInfo = new IntervalInfo(duration, setCount, ranges);
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
        private Boolean isFinished;
    }

}
