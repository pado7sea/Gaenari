package com.example.gaenari.dto.response;

import java.util.List;

public class FavoriteResponseDto {
    private Long programId;
    private String programTitle;
    private int usageCount; // 운동 프로그램 총 사용횟수
    private int finishedCount; // 운동 프로그램 완주 횟수
    private String type; // enum: D(거리목표), T(시간목표), I(인터벌)
    private ProgramTypeInfoDto program;

    class ProgramTypeInfoDto {
        private Double targetValue;
        private IntervalDto intervalInfo;

        public ProgramTypeInfoDto() {
        }

        public Double getTargetValue() {
            return targetValue;
        }

        public void setTargetValue(Double targetValue) {
            this.targetValue = targetValue;
        }

        public IntervalDto getIntervalInfo() {
            return intervalInfo;
        }

        public void setIntervalInfo(IntervalDto intervalInfo) {
            this.intervalInfo = intervalInfo;
        }

        @Override
        public String toString() {
            return "ProgramTypeInfoDto{" +
                    "targetValue=" + targetValue +
                    ", intervalInfo=" + intervalInfo +
                    '}';
        }

        class IntervalDto {
            private Double duration;  // 인터벌 총 소요 시간
            private Integer setCount;  // 세트 수
            private Integer rangeCount; // 세트 당 구간 수
            private List<RangeDto> ranges; // 구간 리스트

            public IntervalDto() {
            }

            public Double getDuration() {
                return duration;
            }

            public void setDuration(Double duration) {
                this.duration = duration;
            }

            public Integer getSetCount() {
                return setCount;
            }

            public void setSetCount(Integer setCount) {
                this.setCount = setCount;
            }

            public Integer getRangeCount() {
                return rangeCount;
            }

            public void setRangeCount(Integer rangeCount) {
                this.rangeCount = rangeCount;
            }

            public List<RangeDto> getRanges() {
                return ranges;
            }

            public void setRanges(List<RangeDto> ranges) {
                this.ranges = ranges;
            }

            @Override
            public String toString() {
                return "IntervalDto{" +
                        "duration=" + duration +
                        ", setCount=" + setCount +
                        ", rangeCount=" + rangeCount +
                        ", ranges=" + ranges +
                        '}';
            }

            class RangeDto{
                private Long id;
                private Boolean isRunning;  // true:뛰는시간, false:걷는시간
                private Double time;  // 단위: sec
                private Double speed; // 단위: km/h

                public RangeDto() {
                }

                public Long getId() {
                    return id;
                }

                public void setId(Long id) {
                    this.id = id;
                }

                public Boolean getRunning() {
                    return isRunning;
                }

                public void setRunning(Boolean running) {
                    isRunning = running;
                }

                public Double getTime() {
                    return time;
                }

                public void setTime(Double time) {
                    this.time = time;
                }

                public Double getSpeed() {
                    return speed;
                }

                public void setSpeed(Double speed) {
                    this.speed = speed;
                }

                @Override
                public String toString() {
                    return "RangeDto{" +
                            "id=" + id +
                            ", isRunning=" + isRunning +
                            ", time=" + time +
                            ", speed=" + speed +
                            '}';
                }
            }
        }
    }

    public FavoriteResponseDto() {
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public int getFinishedCount() {
        return finishedCount;
    }

    public void setFinishedCount(int finishedCount) {
        this.finishedCount = finishedCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ProgramTypeInfoDto getProgram() {
        return program;
    }

    public void setProgram(ProgramTypeInfoDto program) {
        this.program = program;
    }

    @Override
    public String toString() {
        return "FavoriteResponseDto{" +
                "programId=" + programId +
                ", programTitle='" + programTitle + '\'' +
                ", usageCount=" + usageCount +
                ", finishedCount=" + finishedCount +
                ", type='" + type + '\'' +
                ", program=" + program +
                '}';
    }
}
