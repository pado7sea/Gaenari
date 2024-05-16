package com.gaenari.backend.domain.recordDetail.service.impl;

import com.gaenari.backend.domain.client.challenge.ChallengeServiceClient;
import com.gaenari.backend.domain.client.challenge.dto.ChallengeDto;
import com.gaenari.backend.domain.client.challenge.dto.RewardDto;
import com.gaenari.backend.domain.client.challenge.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.client.program.ProgramServiceClient;
import com.gaenari.backend.domain.client.program.dto.ProgramDetailAboutRecordDto;
import com.gaenari.backend.domain.record.dto.enumType.ExerciseType;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import com.gaenari.backend.domain.record.entity.Moment;
import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.entity.RecordChallenge;
import com.gaenari.backend.domain.record.repository.RecordRepository;
import com.gaenari.backend.domain.recordDetail.dto.IntervalDto;
import com.gaenari.backend.domain.recordDetail.dto.ProgramInfoDto;
import com.gaenari.backend.domain.recordDetail.dto.RangeDto;
import com.gaenari.backend.domain.recordDetail.dto.RecordDetailDto;
import com.gaenari.backend.domain.recordDetail.service.RecordDetailService;
import com.gaenari.backend.global.exception.feign.ConnectFeignFailException;
import com.gaenari.backend.global.exception.program.IntervalInfoNotFoundException;
import com.gaenari.backend.global.exception.record.RecordNotFoundException;
import com.gaenari.backend.global.format.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecordDetailServiceImpl implements RecordDetailService {

    private final RecordRepository recordRepository;
    private final ProgramServiceClient programServiceClient;
    private final ChallengeServiceClient challengeServiceClient;

    @Override
    public RecordDetailDto getExerciseRecordDetail(String accountId, Long recordId) {
        Record record = recordRepository.findByAccountIdAndId(accountId, recordId);
        if (record == null) {
            throw new RecordNotFoundException();
        }

        return buildRecordDetailDto(record);
    }

    // ExerciseDetailDto 객체 구성
    private RecordDetailDto buildRecordDetailDto(Record record) {
        List<ChallengeDto> challenges = fetchChallenges(record);
        return RecordDetailDto.builder()
                .exerciseId(record.getId())
                .date(record.getDate())
                .exerciseType(record.getExerciseType())
                .programType(record.getProgramType())
                .program(buildProgramInfo(record))
                .record(buildRecordDto(record))
                .paces(buildPaceDto(record))
                .heartrates(buildHeartrateDto(record))
                .trophies(extractTrophies(challenges))
                .missions(extractMissions(challenges))
                .attainableCoin(getAttainableRewards(record.getAccountId(), record.getId()).getCoin())
                .attainableHeart(getAttainableRewards(record.getAccountId(), record.getId()).getHeart())
                .build();
    }

    // ProgramDto, IntervalDto, RangeDto 는 programType 에 따라 설정
    private ProgramInfoDto buildProgramInfo(Record record) {
        if (record.getExerciseType() == ExerciseType.P && record.getProgramId() != null) {

            ProgramDetailAboutRecordDto program = fetchProgramDetail(record.getProgramId());

            return ProgramInfoDto.builder()
                    .programId(record.getProgramId())
                    .programTitle(program.getProgramTitle())
                    .targetValue(determineTargetValue(record))
                    .intervalInfo(constructIntervalDto(record))
                    .build();
        }
        return null;
    }

    // 타겟 값 설정
    private Double determineTargetValue(Record record) {
        switch (record.getProgramType()) {
            case D:
                return record.getDistance();
            case T:
                return record.getTime();
            default:
                return null; // I 타입은 IntervalDto에서 처리
        }
    }

    private IntervalDto constructIntervalDto(Record record) {
        if (record.getProgramType() == ProgramType.I && record.getRanges() == null) {
            throw new IntervalInfoNotFoundException();
        }

        // Interval 정보 구성 로직
        List<RangeDto> ranges = record.getRanges().stream()
                .map(range -> RangeDto.builder()
                        .id(range.getId())
                        .isRunning(range.getIsRunning())
                        .time(range.getTime())
                        .speed(range.getSpeed())
                        .build())
                .toList();

        double totalDuration = ranges.stream().mapToDouble(RangeDto::getTime).sum();

        // 마이크로 서비스간 통신을 통해 프로그램 정보 가져오기
        ProgramDetailAboutRecordDto programDetailDto = fetchProgramDetail(record.getProgramId());

        // 프로그램 정보가 널이 아닌지 확인 후, 인터벌 정보를 구성
        if (programDetailDto != null && programDetailDto.getProgram() != null && programDetailDto.getProgram().getIntervalInfo() != null) {
            return IntervalDto.builder()
                    .duration(totalDuration)
                    .setCount(programDetailDto.getProgram().getIntervalInfo().getSetCount())
                    .rangeCount(programDetailDto.getProgram().getIntervalInfo().getRangeCount())
                    .ranges(ranges)
                    .build();
        } else {
            // 프로그램 정보가 없는 경우 기본값 설정
            return IntervalDto.builder()
                    .duration(totalDuration)
                    .setCount(0) // 기본값으로 설정
                    .rangeCount(0) // 기본값으로 설정
                    .ranges(ranges)
                    .build();
        }
    }

    // 마이크로 서비스 간 통신을 통해 프로그램 정보 가져오기
    private ProgramDetailAboutRecordDto fetchProgramDetail(Long programId) {
        ResponseEntity<GenericResponse<ProgramDetailAboutRecordDto>> response = programServiceClient.getProgramDetailById(programId);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ConnectFeignFailException();
        }
        return response.getBody().getData();
    }

    // Record 관련 정보 설정
    private RecordDetailDto.DetailRecordDto buildRecordDto(Record record) {
        return RecordDetailDto.DetailRecordDto.builder()
                .distance(record.getDistance())
                .time(record.getTime())
                .cal(record.getCal())
                .build();
    }

    // Pace 정보 설정
    private RecordDetailDto.PaceDto buildPaceDto(Record record) {
        return RecordDetailDto.PaceDto.builder()
                .average(record.getAveragePace())
                .arr(record.getMoments().stream().map(Moment::getPace).toList())
                .build();
    }

    // Heart rate 정보 설정
    private RecordDetailDto.HeartrateDto buildHeartrateDto(Record record) {
        // 심박수 데이터를 추출
        List<Integer> heartbeats = record.getMoments().stream()
                .map(Moment::getHeartrate)
                .filter(Objects::nonNull) // null 값 제거
                .toList();

        // 최대/최소 심박수 계산
        int maxHeartbeat = heartbeats.stream()
                .mapToInt(Integer::intValue) // IntStream 으로 변환
                .max() // 최대값 계산
                .orElse(0); // 만약 값이 없을 경우 0 반환

        int minHeartbeat = heartbeats.stream()
                .mapToInt(Integer::intValue) // IntStream 으로 변환
                .min() // 최소값 계산
                .orElse(0); // 만약 값이 없을 경우 0 반환

        return RecordDetailDto.HeartrateDto.builder()
                .average(record.getAverageHeartRate())
                .max(maxHeartbeat)
                .min(minHeartbeat)
                .arr(heartbeats)
                .build();
    }

    // 마이크로 서비스 간 통신을 통해 도전과제 (업적, 미션) 가져오기
    public List<ChallengeDto> fetchChallenges(Record record) {
        List<Integer> challengeIds = record.getRecordChallenges().stream()
                .map(RecordChallenge::getChallengeId)
                .toList();

        ResponseEntity<GenericResponse<List<ChallengeDto>>> response = challengeServiceClient.getChallenges(challengeIds);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ConnectFeignFailException();
        }
        return response.getBody().getData();
    }

    // 업적만 추출
    private List<RecordDetailDto.TrophyDto> extractTrophies(List<ChallengeDto> challenges) {
        return challenges.stream()
                .filter(challenge -> challenge.getCategory() == ChallengeCategory.TROPHY)
                .map(challenge -> RecordDetailDto.TrophyDto.builder()
                        .id(challenge.getId())
                        .type(challenge.getType())
                        .value(challenge.getValue())
                        .coin(challenge.getCoin())
                        .build())
                .toList();
    }

    // 미션만 추출
    private List<RecordDetailDto.MissionDto> extractMissions(List<ChallengeDto> challenges) {
        return challenges.stream()
                .filter(challenge -> challenge.getCategory() == ChallengeCategory.MISSION)
                .map(challenge -> RecordDetailDto.MissionDto.builder()
                        .id(challenge.getId())
                        .type(challenge.getType())
                        .value(challenge.getValue())
                        .coin(challenge.getCoin())
                        .heart(challenge.getHeart())
                        .build())
                .toList();
    }

    // 마이크로 서비스 간 통신을 통해 얻을 수 있는 코인, 애정도 값 가져오기
    private RewardDto getAttainableRewards(String accountId, Long programId) {
        ResponseEntity<GenericResponse<RewardDto>> response = challengeServiceClient.getAttainableRewards(accountId, programId);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ConnectFeignFailException();
        }
        return response.getBody().getData();
    }


}
