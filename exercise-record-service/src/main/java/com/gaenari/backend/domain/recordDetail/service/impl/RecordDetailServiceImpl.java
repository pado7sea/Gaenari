package com.gaenari.backend.domain.recordDetail.service.impl;

import com.gaenari.backend.domain.client.ChallengeServiceClient;
import com.gaenari.backend.domain.client.dto.ChallengeDto;
import com.gaenari.backend.domain.client.dto.ProgramDetailAboutRecordDto;
import com.gaenari.backend.domain.client.dto.enumType.ChallengeCategory;
import com.gaenari.backend.domain.record.entity.Moment;
import com.gaenari.backend.domain.client.ProgramServiceClient;
import com.gaenari.backend.domain.program.dto.ProgramDetailDto;
import com.gaenari.backend.domain.record.dto.enumType.ExerciseType;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import com.gaenari.backend.domain.recordChallenge.entity.RecordChallenge;
import com.gaenari.backend.domain.recordDetail.dto.IntervalDto;
import com.gaenari.backend.domain.recordDetail.dto.ProgramInfoDto;
import com.gaenari.backend.domain.recordDetail.dto.RangeDto;
import com.gaenari.backend.domain.recordDetail.dto.RecordDetailDto;
import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.repository.RecordRepository;
import com.gaenari.backend.domain.recordDetail.service.RecordDetailService;
import com.gaenari.backend.global.exception.record.RecordAccessException;
import com.gaenari.backend.global.exception.record.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordDetailServiceImpl implements RecordDetailService {

    private final RecordRepository recordRepository;
    private final ProgramServiceClient programServiceClient;
    private final ChallengeServiceClient challengeServiceClient;

    @Override
    public RecordDetailDto getExerciseRecordDetail(String memberId, Long recordId) {

        Optional<Record> record = recordRepository.findById(recordId);

        if (record.isEmpty()) {
            throw new RecordNotFoundException();
        }

        // 프로그램 생성자 ID와 요청한 사용자 ID를 확인
        if (!record.get().getMemberId().equals(memberId)) {
            throw new RecordAccessException();
        }

        return convertToExerciseDetailDto(record.get());
    }

    private RecordDetailDto convertToExerciseDetailDto(Record record) {
        // Record 관련 정보 설정
        RecordDetailDto.RecordDto recordDto = RecordDetailDto.RecordDto.builder()
                .distance(record.getDistance())
                .time(record.getTime())
                .cal(record.getCal())
                .build();

        // Pace 정보 설정
        RecordDetailDto.PaceDto paceDto = RecordDetailDto.PaceDto.builder()
                .average(record.getAveragePace())
                .arr(record.getMoments().stream().map(Moment::getPace).toList())
                .build();

        // Heart rate 정보 설정
        // 심박수 데이터를 추출
        List<Integer> heartrates = record.getMoments().stream()
                .map(Moment::getHeartrate)
                .filter(Objects::nonNull) // null 값 제거
                .toList();

        // 최대/최소 심박수 계산
        int maxHeartrate = heartrates.stream()
                .mapToInt(Integer::intValue) // IntStream으로 변환
                .max() // 최대값 계산
                .orElse(0); // 만약 값이 없을 경우 0 반환

        int minHeartrate = heartrates.stream()
                .mapToInt(Integer::intValue) // IntStream으로 변환
                .min() // 최소값 계산
                .orElse(0); // 만약 값이 없을 경우 0 반환

        RecordDetailDto.HeartrateDto heartrateDto = RecordDetailDto.HeartrateDto.builder()
                .average(record.getAverageHeartRate())
                .max(maxHeartrate)
                .min(minHeartrate)
                .arr(heartrates)
                .build();

        // ProgramDto, IntervalDto, RangeDto는 programType에 따라 설정
        ProgramInfoDto programInfoDto = null;
        if (record.getExerciseType() == ExerciseType.P && record.getProgramId() != null) {
            programInfoDto = ProgramInfoDto.builder()
                    .programId(record.getProgramId())
                    .targetValue(determineTargetValue(record))
                    .intervalInfo(constructIntervalDto(record))
                    .build();
        }

        // 도전과제 정보 가져오기
        List<ChallengeDto> challenges = getChallengesByRecordId(record);

        // 트로피 리스트 추출
        // 미션 리스트 추출
        // 코인 계산
        // 애정도 계산
        List<RecordDetailDto.TrophyDto> trophyDtos = new ArrayList<>();
        List<RecordDetailDto.MissionDto> missionDtos = new ArrayList<>();
        int attainableCoin = 0;
        int attainableHeart = 0;

        for (ChallengeDto challenge : challenges) {
            if (challenge.getCategory() == ChallengeCategory.TROPHY) {
                trophyDtos.add(RecordDetailDto.TrophyDto.builder()
                        .id(challenge.getId())
                        .type(challenge.getType())
                        .coin(challenge.getCoin())
                        .build());
            } else if (challenge.getCategory() == ChallengeCategory.MISSION) {
                missionDtos.add(RecordDetailDto.MissionDto.builder()
                        .id(challenge.getId())
                        .type(challenge.getType())
                        .value(challenge.getValue())
                        .coin(challenge.getCoin())
                        .heart(challenge.getHeart())
                        .build());
            }
            attainableCoin += challenge.getCoin();
            attainableHeart += challenge.getHeart();
        }

        // ExerciseDetailDto 객체 구성
        return RecordDetailDto.builder()
                .exerciseId(record.getId())
                .date(record.getDate())
                .exerciseType(record.getExerciseType())
                .programType(record.getProgramType())
                .program(programInfoDto)
                .record(recordDto)
                .paces(paceDto)
                .heartrates(heartrateDto)
                .trophies(trophyDtos)   // 트로피 리스트
                .missions(missionDtos)  // 미션 리스트
                .attainableCoin(attainableCoin) // 코인
                .attainableHeart(attainableHeart) // 애정도
                .build();

    }

    // 마이크로 서비스 간 통신을 통해 도전과제 (업적, 미션) 가져오기
    public List<ChallengeDto> getChallengesByRecordId(Record record) {
        List<Integer> challengeIds = record.getRecordChallenges().stream()
                .map(RecordChallenge::getChallengeId)
                .toList();

        return challengeServiceClient.getChallenges(challengeIds);
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
        if (record.getProgramType() != ProgramType.I || record.getRanges() == null) {
            return null;
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
        ProgramDetailAboutRecordDto programDetailDto = programServiceClient.getProgramDetailById(record.getProgramId());

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

}
