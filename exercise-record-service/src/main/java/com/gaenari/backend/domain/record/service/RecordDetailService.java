package com.gaenari.backend.domain.record.service;

import com.gaenari.backend.domain.record.dto.responseDto.RecordDetailDto;

public interface RecordDetailService {

    RecordDetailDto getExerciseRecordDetail(Long memberId, Long exerciseId);

}
