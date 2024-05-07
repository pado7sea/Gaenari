package com.gaenari.backend.domain.recordFeign.service.impl;

import com.gaenari.backend.domain.program.dto.ProgramDetailDto;
import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.repository.RecordRepository;
import com.gaenari.backend.domain.recordFeign.service.RecordFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordFeignServiceImpl implements RecordFeignService {

    private final RecordRepository recordRepository;

    @Override
    public List<ProgramDetailDto.UsageLogDto> getRecordsByProgramId(Long programId) {

        List<Record> records = recordRepository.findByProgramId(programId);

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
                .collect(Collectors.toList());
    }
}
