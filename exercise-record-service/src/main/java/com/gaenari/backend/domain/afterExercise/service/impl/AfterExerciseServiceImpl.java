package com.gaenari.backend.domain.afterExercise.service.impl;

import com.gaenari.backend.domain.afterExercise.dto.requestDto.SaveExerciseRecordDto;
import com.gaenari.backend.domain.afterExercise.service.AfterExerciseService;
import com.gaenari.backend.domain.client.challenge.ChallengeServiceClient;
import com.gaenari.backend.domain.client.member.MemberServiceClient;
import com.gaenari.backend.domain.client.program.ProgramServiceClient;
import com.gaenari.backend.domain.client.program.dto.ProgramDetailAboutRecordDto;
import com.gaenari.backend.domain.client.challenge.dto.RecordAboutChallengeDto;
import com.gaenari.backend.domain.record.dto.enumType.ExerciseType;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import com.gaenari.backend.domain.record.entity.IntervalRangeRecord;
import com.gaenari.backend.domain.record.entity.Moment;
import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.repository.RecordRepository;
import com.gaenari.backend.domain.record.entity.RecordChallenge;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;
import com.gaenari.backend.domain.statistic.entity.Statistic;
import com.gaenari.backend.domain.statistic.repository.StatisticRepository;
import com.gaenari.backend.global.exception.program.ProgramNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final MemberServiceClient memberServiceClient;

    // 운동 통계 업데이트
    @Override
    public TotalStatisticDto updateExerciseStatistics(String memberId, SaveExerciseRecordDto exerciseDto) {
        // 누적 통계를 찾고, 없다면 첫 기록으로 간주하여 새로운 통계 객체 생성
        Statistic currentStats = statisticRepository.findByMemberId(memberId);
        if (currentStats == null) currentStats = createInitialStatistic(memberId);

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
    public Integer updateProgramUsageCount(String memberId, SaveExerciseRecordDto exerciseDto) {
        // ExerciseDto에서 운동 및 프로그램 정보 추출
        ExerciseType exerciseType = exerciseDto.getExerciseType();  // 운동 유형
        ProgramType programType = exerciseDto.getProgramType();     // 프로그램 유형
        SaveExerciseRecordDto.ProgramInfoDto programDto = exerciseDto.getProgram(); // 프로그램 정보

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

    // 초기 통계 생성 메서드
    private Statistic createInitialStatistic(String memberId) {
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

    // 운동 후 기록 저장 메서드
    @Override
    public Long saveExerciseRecord(String memberId, SaveExerciseRecordDto exerciseDto) {
        // ExerciseDto에서 운동 및 프로그램 정보 추출
        ExerciseType exerciseType = exerciseDto.getExerciseType();  // 운동 유형
        ProgramType programType = exerciseDto.getProgramType();     // 프로그램 유형
        SaveExerciseRecordDto.ProgramInfoDto programDto = exerciseDto.getProgram(); // 프로그램 정보

        // 프로그램 타입이 P가 아닌 경우에는 프로그램 관련 정보가 없어야 함
        if (exerciseType != ExerciseType.P && (programType != null || programDto != null)) {
            throw new IllegalArgumentException("ProgramType과 ProgramDto는 ExerciseType이 P일 때만 유효합니다.");
        }

        // 운동이 프로그램 타입인 경우, 해당하는 프로그램 정보가 있는지 확인
        if (exerciseType == ExerciseType.P) {
            if (exerciseDto.getProgram() == null) {
                throw new ProgramNotFoundException();
            }

            // 마이크로 서비스 간 통신을 통해 프로그램 정보 가져오기
            ProgramDetailAboutRecordDto programDetailDto = programServiceClient.getProgramDetailById(programDto.getProgramId());

            // 프로그램 타입에 따라 필요한 정보가 있는지 확인
            if (programType == ProgramType.D || programType == ProgramType.T) {
                if (programDetailDto.getProgram().getTargetValue() == null) {
                    throw new IllegalArgumentException(programType + " 타입에서는 targetValue가 필요합니다.");
                }
            } else if (programType == ProgramType.I) {
                if (programDetailDto.getProgram().getIntervalInfo() == null) {
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
                .memberId(memberId)
                .recordId(null)
                .distance(exerciseDto.getRecord().getDistance())
                .time(exerciseDto.getRecord().getTime())
                .statisticDistance(statisticRepository.findByMemberId(memberId).getDist())
                .statisticTime(statisticRepository.findByMemberId(memberId).getTime())
                .build();

        // 마이크로 서비스간 통신을 통해 도전과제(아이디) 가져오기
        // TODO : -> 도전 과제 리스트 가져오기 해서 두개 하나로 합쳐서 할지 고민되네..
        List<Integer> challengeIds = challengeServiceClient.getNewlyAchievedChallengeIds(recordAboutChallengeDto);
//        List<ChallengeDto> challengeDtos = challengeServiceClient.getChallenges(challengeIds);
        for (Integer challengeId : challengeIds) {
            RecordChallenge recordChallenge = RecordChallenge.builder()
                    .record(null)
                    .challengeId(challengeId)
                    .build();
            recordChallenges.add(recordChallenge);
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
                .cal(calculateCal(memberId, exerciseDto)) // 칼로리 계산하는 메서드 따로 뺄 것.
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

    // 칼로리 계산 메서드(메켈스식(Metcalfe's Law))
    private Double calculateCal(String memberId, SaveExerciseRecordDto exerciseDto) {
        // 운동 시간과 속도를 ExerciseDto로부터 추출
        double timeInSeconds = exerciseDto.getRecord().getTime(); // 운동 시간(초 단위)
        double speedInKmPerHour = exerciseDto.getSpeeds().getAverage(); // 평균 속도(km/h)

        // MET 값을 계산하는 calculateMet 메소드를 호출하여 MET 값을 구함
        double met = calculateMet(timeInSeconds, speedInKmPerHour);

        // 마이크로 서비스 간 통신을 통해 체중 가져오기
        ResponseEntity<?> response = memberServiceClient.getWeight(memberId);
        int weight = response.getStatusCode().value();

        return met * weight * (timeInSeconds / 3600.0); // 시간을 시간 단위로 변환하여 계산
    }

    // MET(Metabolic Equivalent of Task, 활동 강도 지수) 값 계산 메서드
    private Double calculateMet(double timeInSeconds, double speedInKmPerHour) {
        // 운동 강도에 따른 속도와 운동 시간을 이용하여 MET 값을 계산
        double met = speedInKmPerHour * 0.1 + 3.5;
        // 운동 시간을 곱하여 최종 MET 값을 결정
        met *= timeInSeconds / 3600.0; // 시간을 시간 단위로 변환하여 계산

        return met;
    }

    // 운동 완주 여부 판단 메서드
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

                // 운동 기록에서 실제 시간을 가져옴
                double actualTimeI = exerciseDto.getRecord().getTime();

                // 인터벌 프로그램 완주 여부 판단
                return actualTimeI >= targetTimeI;

            case D: // 거리 목표 프로그램인 경우
                if (programDetailDto.getProgram().getTargetValue() == null) {
                    throw new IllegalArgumentException("targetValue가 null입니다.");
                }
                double targetDistanceD = programDetailDto.getProgram().getTargetValue();
                double actualDistanceD = exerciseDto.getRecord().getDistance();

                return actualDistanceD >= targetDistanceD;

            case T: // 시간 목표 프로그램인 경우
                if (programDetailDto.getProgram().getTargetValue() == null) {
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