package com.gaenari.backend.domain.afterExercise.service.impl;

import com.gaenari.backend.domain.afterExercise.dto.requestDto.NoticeInfoDto;
import com.gaenari.backend.domain.afterExercise.dto.requestDto.SaveExerciseRecordDto;
import com.gaenari.backend.domain.afterExercise.service.AfterExerciseService;
import com.gaenari.backend.domain.client.challenge.ChallengeServiceClient;
import com.gaenari.backend.domain.client.challenge.dto.RecordAboutChallengeDto;
import com.gaenari.backend.domain.client.member.MemberServiceClient;
import com.gaenari.backend.domain.client.program.ProgramServiceClient;
import com.gaenari.backend.domain.client.program.dto.ProgramDetailAboutRecordDto;
import com.gaenari.backend.domain.record.dto.enumType.ExerciseType;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import com.gaenari.backend.domain.record.entity.IntervalRangeRecord;
import com.gaenari.backend.domain.record.entity.Moment;
import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.entity.RecordChallenge;
import com.gaenari.backend.domain.record.repository.RecordRepository;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;
import com.gaenari.backend.domain.statistic.entity.Statistic;
import com.gaenari.backend.domain.statistic.repository.StatisticRepository;
import com.gaenari.backend.global.exception.feign.ConnectFeignFailException;
import com.gaenari.backend.global.exception.program.IntervalInfoNotFoundException;
import com.gaenari.backend.global.exception.program.ProgramNotFoundException;
import com.gaenari.backend.global.exception.program.TargetValueNotFoundException;
import com.gaenari.backend.global.format.response.GenericResponse;
import com.gaenari.backend.global.util.fcm.dto.FcmMessageRequestDto;
import java.time.format.DateTimeFormatter;
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

    // 프로그램 사용 횟수 업데이트
    @Override
    public void updateProgramUsageCount(String accountId, SaveExerciseRecordDto exerciseDto) {
        if(validateProgramInfo(exerciseDto)) {
          programServiceClient.updateProgramUsageCount(exerciseDto.getProgram().getProgramId()).getBody().getData();
        }
    }

    private Boolean validateProgramInfo(SaveExerciseRecordDto dto) {
        if(dto.getExerciseType() == ExerciseType.P && dto.getProgram() == null){
            throw new ProgramNotFoundException();
        }
        return dto.getExerciseType() == ExerciseType.P;
    }

    private void validateIntervalInfo(SaveExerciseRecordDto dto) {
        if (dto.getProgramType() == ProgramType.I && dto.getProgram() == null) {
            throw new IntervalInfoNotFoundException();
        }
    }

    // 운동 후 기록 저장 메서드
    @Override
    public Long saveExerciseRecord(String accountId, SaveExerciseRecordDto exerciseDto) {
        validateIntervalInfo(exerciseDto);
        Record record = buildRecord(accountId, exerciseDto);
        recordRepository.save(record);
        return record.getId();
    }

    private Record buildRecord(String accountId, SaveExerciseRecordDto exerciseDto) {
        List<IntervalRangeRecord> ranges = buildIntervalRangeRecords(accountId, exerciseDto);
        List<Moment> moments = buildMoments(exerciseDto);
        List<RecordChallenge> recordChallenges = buildRecordChallenges(accountId, exerciseDto);
        Boolean isProgram = validateProgramInfo(exerciseDto);

        // Record 객체 생성
        Record record = Record.builder()
                .accountId(accountId)
                .exerciseType(exerciseDto.getExerciseType())
                .programType(exerciseDto.getProgramType())
                .programId(isProgram ? exerciseDto.getProgram().getProgramId() : null)
                .date(exerciseDto.getDate())
                .time(exerciseDto.getRecord().getTime())
                .distance(exerciseDto.getRecord().getDistance())
                .averagePace(exerciseDto.getSpeeds().getAverage() != 0 ? (3600 / exerciseDto.getSpeeds().getAverage()) : 0)
                .averageHeartRate(exerciseDto.getHeartrates().getAverage())
                .cal(calculateCal(accountId, exerciseDto))
                .isFinished(isProgram ? determineFinish(exerciseDto) : false) // 완주 여부
                .ranges(ranges)
                .moments(moments)
                .recordChallenges(recordChallenges) // 달성한 도전 과제
                .build();

        // 양방향 매핑 업데이트
        ranges.forEach(range -> range.updateRecord(record));
        moments.forEach(moment -> moment.updateRecord(record));
        recordChallenges.forEach(recordChallenge -> recordChallenge.updateRecord(record));

        return record;
    }

    // DTO -> Interval Range Record 정보 설정
    private List<IntervalRangeRecord> buildIntervalRangeRecords(String accountId, SaveExerciseRecordDto exerciseDto) {
        List<IntervalRangeRecord> ranges = new ArrayList<>();
        if (exerciseDto.getExerciseType() == ExerciseType.P && exerciseDto.getProgram().getIntervalInfo() != null) {
            exerciseDto.getProgram().getIntervalInfo().getRanges().forEach(rangeDto ->
                    ranges.add(IntervalRangeRecord.builder()
                            .accountId(accountId)
                            .isRunning(rangeDto.getIsRunning())
                            .time(rangeDto.getTime())
                            .speed(rangeDto.getSpeed())
                            .build())
            );
        }
        return ranges;
    }

    // DTO -> Moment 정보 설정
    private List<Moment> buildMoments(SaveExerciseRecordDto exerciseDto) {
        List<Moment> moments = new ArrayList<>();
        if (exerciseDto.getRecord().getTime() != null) {
            for (int i = 0; i < exerciseDto.getSpeeds().getArr().size(); i++) {
                double speed = exerciseDto.getSpeeds().getArr().get(i);
                Integer heartbeat = exerciseDto.getHeartrates().getArr().size() > i ? exerciseDto.getHeartrates().getArr().get(i) : null;
                moments.add(Moment.builder()
                        .heartrate(heartbeat)
                        .distance(speed * 60 / 3600) // 시속 km/h를 m/min 으로 변환
                        .pace(speed == 0 ? 0 : 3600 / speed) // 분당 페이스 계산, 속도가 0이면 null 로 설정
                        .build());
            }
        }
        return moments;
    }

    // DTO -> RecordChallenge 정보 설정
    private List<RecordChallenge> buildRecordChallenges(String accountId, SaveExerciseRecordDto exerciseDto) {
        List<RecordChallenge> recordChallenges = new ArrayList<>();
        if (exerciseDto.getRecord().getDistance() > 0) {
            Statistic statistic = statisticRepository.findByAccountId(accountId);
            double statisticDistance = statistic != null ? statistic.getDist() : 0;
            double statisticTime = statistic != null ? statistic.getTime() : 0;

            RecordAboutChallengeDto challengeDto = RecordAboutChallengeDto.builder()
                    .accountId(accountId)
                    .recordId(null)
                    .distance(exerciseDto.getRecord().getDistance())
                    .time(exerciseDto.getRecord().getTime())
                    .statisticDistance(statisticDistance)
                    .statisticTime(statisticTime)
                    .build();

            // 마이크로 서비스간 통신을 통해 도전과제(아이디) 가져오기
            List<Integer> challengeIds = fetchNewlyAchievedChallengeIds(challengeDto);
            challengeIds.forEach(challengeId -> recordChallenges.add(RecordChallenge.builder()
                    .record(null)
                    .challengeId(challengeId)
                    .build()));
        }
        return recordChallenges;
    }

    // 마이크로 서비스간 통신을 통해 도전과제(아이디) 가져오기
    private List<Integer> fetchNewlyAchievedChallengeIds(RecordAboutChallengeDto challengeDto) {
        ResponseEntity<GenericResponse<List<Integer>>> response = challengeServiceClient.getNewlyAchievedChallengeIds(challengeDto);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ConnectFeignFailException();
        }
        return response.getBody().getData();
    }


    // 마이크로 서비스간 통신을 통해 프로그램 정보 가져오기
    private ProgramDetailAboutRecordDto fetchProgramDetail(Long programId) {
        ResponseEntity<GenericResponse<ProgramDetailAboutRecordDto>> response = programServiceClient.getProgramDetailById(programId);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ConnectFeignFailException();
        }
        return response.getBody().getData();
    }

    // 칼로리 계산 메서드(메켈스식(Metcalfe's Law))
    private Double calculateCal(String accountId, SaveExerciseRecordDto exerciseDto) {
        double timeInHours = exerciseDto.getRecord().getTime() / 3600.0; // 초 단위를 시간 단위로 변환
        double speedInKmPerHour = exerciseDto.getSpeeds().getAverage(); // 평균 속도(km/h)
        double met = speedInKmPerHour * 0.1 + 3.5;
        double weight = fetchMemberWeight(accountId);

        return met * weight * timeInHours; // 칼로리 계산
    }

    // 마이크로 서비스 간 통신을 통해 체중 가져오기
    private double fetchMemberWeight(String accountId) {
        ResponseEntity<GenericResponse<Integer>> response = memberServiceClient.getWeight(accountId);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ConnectFeignFailException();
        }
        return response.getBody().getData();
    }

    // 운동 완주 여부 판단 메서드
    private Boolean determineFinish(SaveExerciseRecordDto exerciseDto) {
        // 마이크로 서비스간 통신을 통해 프로그램 정보 가져오기
        ProgramDetailAboutRecordDto programDetailDto = fetchProgramDetail(exerciseDto.getProgram().getProgramId());

        // 프로그램 타입이 아닐 경우 기본적으로 미완주 처리
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
                    throw new TargetValueNotFoundException();
                }
                double targetDistanceD = programDetailDto.getProgram().getTargetValue();
                double actualDistanceD = exerciseDto.getRecord().getDistance();

                return actualDistanceD >= targetDistanceD;

            case T: // 시간 목표 프로그램인 경우
                if (programDetailDto.getProgram().getTargetValue() == null) {
                    throw new TargetValueNotFoundException();
                }
                double targetTimeT = programDetailDto.getProgram().getTargetValue();
                double actualTimeT = exerciseDto.getRecord().getTime();

                return actualTimeT >= targetTimeT;

            default:
                return false;
        }

    }

    // 운동 통계 업데이트
    @Override
    public TotalStatisticDto updateExerciseStatistics(String accountId, SaveExerciseRecordDto exerciseDto) {
        // 누적 통계를 찾고, 없다면 첫 기록으로 간주하여 새로운 통계 객체 생성
        Statistic currentStats = statisticRepository.findByAccountId(accountId);
        if (currentStats == null) currentStats = createInitialStatistic(accountId);

        // 통계 업데이트
        updateStatisticsWithExerciseData(currentStats, exerciseDto);

        // 통계 저장
        statisticRepository.save(currentStats);

        // 총 통계 정보 반환
        return buildTotalStatisticDto(currentStats);
    }

    @Override
    public void sendFcmNotice(String accountId, NoticeInfoDto infoDto) {
        String date = infoDto.getExerciseDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 HH시 mm분"));
        String content = infoDto.getProgramTitle() + "를 시작했어요!";
        FcmMessageRequestDto.builder()
            .accountId(accountId)
            .title(date)
            .content(content)
            .build();
    }

    // 초기 통계 생성 메서드
    private Statistic createInitialStatistic(String accountId) {
        // 첫 기록을 위한 새로운 Statistic 객체 생성
        return Statistic.builder()
                .accountId(accountId)
                .time(0.0)
                .dist(0.0)
                .cal(0.0)
                .pace(0.0)
                .date(LocalDateTime.now()) // 최초 기록 날짜로 설정
                .count(0)
                .build();
    }

    // 통계 업데이트 메서드
    private void updateStatisticsWithExerciseData(Statistic stats, SaveExerciseRecordDto exerciseDto) {
        double speed = exerciseDto.getSpeeds().getAverage();
        double averagePace = speed == 0 ? 0 : 3600 / speed;
        stats.setDist(stats.getDist() + exerciseDto.getRecord().getDistance());
        stats.setTime(stats.getTime() + exerciseDto.getRecord().getTime());
        stats.setCal(stats.getCal() + calculateCal(stats.getAccountId(), exerciseDto));
        double newAveragePace = (stats.getPace() * stats.getCount() + averagePace) / (stats.getCount() + 1);
        stats.setPace(newAveragePace);
        stats.setCount(stats.getCount() + 1);
        stats.setDate(exerciseDto.getDate());
    }

    private TotalStatisticDto buildTotalStatisticDto(Statistic stats) {
        return TotalStatisticDto.builder()
                .time(stats.getTime())
                .dist(stats.getDist())
                .cal(stats.getCal())
                .pace(stats.getPace())
                .date(stats.getDate())
                .count(stats.getCount())
                .build();
    }

}