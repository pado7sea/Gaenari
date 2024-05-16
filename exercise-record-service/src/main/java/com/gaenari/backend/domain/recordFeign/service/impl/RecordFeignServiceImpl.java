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

        // 레코드에 연결된 도전 과제들의 ID를 저장할 리스트 생성
        List<Integer> challengeIds = new ArrayList<>();

        // 레코드에 연결된 도전 과제들의 ID를 리스트에 저장
        for (RecordChallenge recordChallenge : record.getRecordChallenges()) {
            challengeIds.add(recordChallenge.getChallengeId());
        }

        return challengeIds;
    }
}
