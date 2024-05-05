package com.gaenari.backend.domain.afterExercise.service.impl;

import com.gaenari.backend.domain.client.dto.ProgramDetailAboutRecordDto;
import com.gaenari.backend.domain.client.dto.RecordAboutChallengeDto;
import com.gaenari.backend.domain.afterExercise.dto.requestDto.SaveExerciseRecordDto;
import com.gaenari.backend.domain.afterExercise.service.AfterExerciseService;
import com.gaenari.backend.domain.client.ChallengeServiceClient;
import com.gaenari.backend.domain.client.ProgramServiceClient;
import com.gaenari.backend.domain.program.dto.ProgramDetailDto;
import com.gaenari.backend.domain.record.dto.enumType.ExerciseType;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import com.gaenari.backend.domain.record.entity.IntervalRangeRecord;
import com.gaenari.backend.domain.record.entity.Moment;
import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.repository.RecordRepository;
import com.gaenari.backend.domain.recordChallenge.entity.RecordChallenge;
import com.gaenari.backend.domain.recordDetail.dto.ProgramInfoDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;
import com.gaenari.backend.domain.statistic.entity.Statistic;
import com.gaenari.backend.domain.statistic.repository.StatisticRepository;
import com.gaenari.backend.global.exception.program.ProgramNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AfterExerciseServiceImpl implements AfterExerciseService {

    private final RecordRepository recordRepository;
    private final StatisticRepository statisticRepository;
    private final ProgramServiceClient programServiceClient;
    private final ChallengeServiceClient challengeServiceClient;

    // 운동 통계 업데이트
    @Override
    public TotalStatisticDto updateExerciseStatistics(Long memberId, SaveExerciseRecordDto exerciseDto) {
        // 누적 통계를 찾고, 없다면 첫 기록으로 간주하여 새로운 통계 객체 생성
        Statistic currentStats = statisticRepository.findByMemberId(memberId)
                .orElseGet(() -> createInitialStatistic(memberId));

        // 속도 -> 페이스
        double speed = exerciseDto.getSpeeds().getAverage();
        double averagePace = speed == 0 ? 0 : 3600 / speed;

        // 기존 통계 업데이트 로직
        currentStats.setDist(currentStats.getDist() + exerciseDto.getRecord().getDistance());
        currentStats.setTime(currentStats.getTime() + exerciseDto.getRecord().getTime());
        double newAveragePace = (currentStats.getPace() * currentStats.getCount() + averagePace) / (currentStats.getCount() + 1);
        currentStats.setPace(newAveragePace);
        currentStats.setCount(currentStats.getCount() + 1);
        if (currentStats.getDate() == null || currentStats.getDate().isBefore(exerciseDto.getDate())) {
            currentStats.setDate(exerciseDto.getDate());
        }

        // 통계 저장
        statisticRepository.save(currentStats);

        // 총 통계 정보 반환
        return TotalStatisticDto.builder()
                .time(currentStats.getTime())
                .dist(currentStats.getDist())
                .cal(currentStats.getCal())
                .pace(newAveragePace)
                .date(currentStats.getDate())
                .count(currentStats.getCount())
                .build();
    }

    // 프로그램 사용 횟수 업데이트
    @Override
    public Integer updateProgramUsageCount(Long memberId, SaveExerciseRecordDto exerciseDto) {
        // ExerciseDto에서 운동 및 프로그램 정보 추출
        ExerciseType exerciseType = exerciseDto.getExerciseType();  // 운동 유형
        ProgramType programType = exerciseDto.getProgramType();     // 프로그램 유형
        ProgramInfoDto programDto = exerciseDto.getProgram(); // 프로그램 정보

        // 프로그램 타입이 P가 아닌 경우에는 프로그램 관련 정보가 없어야 함
        if (exerciseType != ExerciseType.P && (programType != null || programDto != null)) {
            throw new IllegalArgumentException("ProgramType과 ProgramDto는 ExerciseType이 P일 때만 유효합니다.");
        }

        // 운동이 프로그램 타입인 경우, 해당하는 프로그램 정보가 있는지 확인
        if (exerciseType == ExerciseType.P) {
            if (exerciseDto.getProgram() == null) {
                throw new ProgramNotFoundException();
            }

            // 마이크로 서비스간 통신을 통해 사용 횟수 1 증가 요청 보내기
            return programServiceClient.updateProgramUsageCount(exerciseDto.getProgram().getProgramId());

        }

        return null;
    }

    // 초기 통계 생성 메소드
    private Statistic createInitialStatistic(Long memberId) {
        // 첫 기록을 위한 새로운 Statistic 객체 생성
        return Statistic.builder()
                .memberId(memberId)
                .time(0.0)
                .dist(0.0)
                .cal(0.0)
                .pace(0.0)
                .date(LocalDateTime.now()) // 최초 기록 날짜로 설정
                .count(0)
                .build();
    }

    // 운동 후 기록 저장 메소드
    @Override
    public Long saveExerciseRecord(Long memberId, SaveExerciseRecordDto exerciseDto) {
        // ExerciseDto에서 운동 및 프로그램 정보 추출
        ExerciseType exerciseType = exerciseDto.getExerciseType();  // 운동 유형
        ProgramType programType = exerciseDto.getProgramType();     // 프로그램 유형
        ProgramInfoDto programDto = exerciseDto.getProgram(); // 프로그램 정보

        // 프로그램 타입이 P가 아닌 경우에는 프로그램 관련 정보가 없어야 함
        if (exerciseType != ExerciseType.P && (programType != null || programDto != null)) {
            throw new IllegalArgumentException("ProgramType과 ProgramDto는 ExerciseType이 P일 때만 유효합니다.");
        }

        // 운동이 프로그램 타입인 경우, 해당하는 프로그램 정보가 있는지 확인
        if (exerciseType == ExerciseType.P) {
            if (exerciseDto.getProgram() == null) {
                throw new ProgramNotFoundException();
            }

            // 프로그램 타입에 따라 필요한 정보가 있는지 확인
            if (programType == ProgramType.D || programType == ProgramType.T) {
                if (exerciseDto.getProgram().getTargetValue() == null) {
                    throw new IllegalArgumentException(programType + " 타입에서는 targetValue가 필요합니다.");
                }
            } else if (programType == ProgramType.I) {
                if (exerciseDto.getProgram().getIntervalInfo() == null) {
                    throw new IllegalArgumentException("Interval 타입에서는 intervalInfo가 필요합니다.");
                }
            }
        }

        // Interval Range Record 정보 설정
        List<IntervalRangeRecord> ranges = new ArrayList<>();
        if (programDto != null && programDto.getIntervalInfo() != null) {
            programDto.getIntervalInfo().getRanges().forEach(rangeDto -> {
                ranges.add(IntervalRangeRecord.builder()
                        .memberId(memberId)
                        .isRunning(rangeDto.getIsRunning())
                        .time(rangeDto.getTime())
                        .speed(rangeDto.getSpeed())
                        .build());
            });
        }

        // Moment 정보 설정
        List<Moment> moments = new ArrayList<>();
        if (exerciseDto.getRecord().getTime() != null) {
            for (int i = 0; i < exerciseDto.getSpeeds().getArr().size(); i++) {
                int speed = exerciseDto.getSpeeds().getArr().get(i);
                Integer heartrate = exerciseDto.getHeartrates().getArr().size() > i ? exerciseDto.getHeartrates().getArr().get(i) : null;
                moments.add(Moment.builder()
                        .heartrate(heartrate)
                        .distance(speed * 60 / 3600) // 시속 km/h를 m/min로 변환
                        .pace(speed == 0 ? null : 3600 / speed) // 분당 페이스 계산, 속도가 0이면 null로 설정
                        .build());
            }
        }

        // RecordChallenge 정보 설정
        List<RecordChallenge> recordChallenges = new ArrayList<>();
        RecordAboutChallengeDto recordAboutChallengeDto = RecordAboutChallengeDto.builder()
                .recordId(null)
                .distance(exerciseDto.getRecord().getDistance())
                .time(exerciseDto.getRecord().getTime())
                .statisticDistance(statisticRepository.findByMemberId(memberId).get().getDist())
                .statisticTime(statisticRepository.findByMemberId(memberId).get().getTime())
                .build();

        // 마이크로 서비스간 통신을 통해 도전과제(아이디) 가져오기
        List<Integer> challengeIds = challengeServiceClient.getAchievedChallengeIds(recordAboutChallengeDto);
        for (Integer challengeId : challengeIds) {
            recordChallenges.add(RecordChallenge.builder()
                    .record(null)
                    .challengeId(challengeId)
                    .isObtained(false)
                    .build());
        }

        // Record 객체 생성
        Record record = Record.builder()
                .memberId(memberId)
                .exerciseType(exerciseType)
                .programType(programType)
                .programId(exerciseDto.getProgram().getProgramId())
                .date(exerciseDto.getDate())
                .time(exerciseDto.getRecord().getTime())
                .distance(exerciseDto.getRecord().getDistance())
                .averagePace((3600 / exerciseDto.getSpeeds().getAverage()))
                .averageHeartRate(exerciseDto.getHeartrates().getAverage())
                .cal(exerciseDto.getRecord().getCal())
                .isFinished(determineFinish(exerciseDto)) // 완주 여부
                .ranges(ranges)
                .moments(moments)
                .recordChallenges(recordChallenges) // 달성한 도전 과제
                .build();

        // 양방향 매핑 업데이트
        ranges.forEach(range -> range.updateRecord(record));
        moments.forEach(moment -> moment.updateRecord(record));
        recordChallenges.forEach(recordChallenge -> recordChallenge.updateRecord(record));

        // Record 저장 후 ID 반환
        return recordRepository.save(record).getId();
    }

    // 운동 완주 여부 판단 메소드
    private Boolean determineFinish(SaveExerciseRecordDto exerciseDto) {
        // 마이크로 서비스간 통신을 통해 프로그램 정보 가져오기
        ProgramDetailAboutRecordDto programDetailDto = programServiceClient.getProgramDetailById(exerciseDto.getProgram().getProgramId());

        if (exerciseDto.getExerciseType() != ExerciseType.P) {
            return false;
        }

        // 프로그램 타입에 따라 완주 여부를 결정
        switch (exerciseDto.getProgramType()) {
            case I: // 인터벌 프로그램인 경우
                // 프로그램 정보에서 시간과 거리 목표를 가져옴
                double targetTimeI = programDetailDto.getProgram().getIntervalInfo().getDuration();
//                double targetDistanceI = programDetailDto.getTargetDistance();

                // 운동 기록에서 실제 시간과 거리를 가져옴
                double actualTimeI = exerciseDto.getRecord().getTime();
                double actualDistanceI = exerciseDto.getRecord().getDistance();

                // 인터벌 프로그램 완주 여부 판단
//                return actualTimeI >= targetTimeI && actualDistanceI >= targetDistanceI;
                return actualTimeI >= targetTimeI;

            case D: // 거리 목표 프로그램인 경우
                if (exerciseDto.getProgram().getTargetValue() == null) {
                    throw new IllegalArgumentException("targetValue가 null입니다.");
                }
                double targetDistanceD = programDetailDto.getProgram().getTargetValue();
                double actualDistanceD = exerciseDto.getRecord().getDistance();

                return actualDistanceD >= targetDistanceD;

            case T: // 시간 목표 프로그램인 경우
                if (exerciseDto.getProgram().getTargetValue() == null) {
                    throw new IllegalArgumentException("targetValue가 null입니다.");
                }
                double targetTimeT = programDetailDto.getProgram().getTargetValue();
                double actualTimeT = exerciseDto.getRecord().getTime();

                return actualTimeT >= targetTimeT;

            default:
                return false;
        }

    }


}