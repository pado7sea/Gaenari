package com.gaenari.backend.domain.recordFeign.service.impl;

import com.gaenari.backend.domain.client.program.dto.ProgramDetailDto;
import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.entity.RecordChallenge;
import com.gaenari.backend.domain.record.repository.RecordRepository;
import com.gaenari.backend.domain.recordFeign.service.RecordFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordFeignServiceImpl implements RecordFeignService {

    private final RecordRepository recordRepository;

    @Override
    public List<ProgramDetailDto.UsageLogDto> getRecordsByProgramId(Long programId) {

        List<Record> records = recordRepository.findByProgramIdOrderByDateDesc(programId);

        return records.stream()
                .map(record -> ProgramDetailDto.UsageLogDto.builder()
                        .recordId(record.getId())
                        .distance(record.getDistance())
                        .averagePace(record.getAveragePace())
                        .time(record.getTime())
                        .date(record.getDate())
                        .cal(record.getCal())
                        .isFinished(record.getIsFinished())
                        .build())
                .toList();
    }

    @Override
    public List<Integer> getChallengeIdsByRecordId(String accountId, Long recordId) {
        // 레코드 ID로 레코드 엔티티를 조회
        Record record = recordRepository.findByAccountIdAndId(accountId, recordId);

        // record의 isObtained가 true이면 빈 리스트 반환
        if (record.getIsObtained()) {
            return new ArrayList<>();
        }

        // 레코드에 연결된 도전 과제들의 ID를 저장할 리스트 생성
        List<Integer> challengeIds = new ArrayList<>();

        // 레코드에 연결된 도전 과제들의 ID를 리스트에 저장
        for (RecordChallenge recordChallenge : record.getRecordChallenges()) {
            challengeIds.add(recordChallenge.getChallengeId());
        }

        return challengeIds;
    }

    @Override
    public void updateRecordObtained(String accountId, Long recordId) {
        // 레코드 ID로 레코드 엔티티를 조회
        Record record = recordRepository.findByAccountIdAndId(accountId, recordId);

        // isObtained 값을 true로 설정
        record.updateObtained(true);

        // 변경 사항을 저장
        recordRepository.save(record);
    }

    @Override
    public void updateAllRecordObtained(String accountId) {
        // 아직 보상이 수령 완료 되지 않은 기록들을 조회
        List<Record> records = recordRepository.findByAccountIdAndIsObtainedFalse(accountId);

        // 각 레코드의 isObtained 값을 true로 설정
        for (Record record : records) {
            record.updateObtained(true);

            // 변경 사항을 저장
            recordRepository.save(record);
        }

    }

}
