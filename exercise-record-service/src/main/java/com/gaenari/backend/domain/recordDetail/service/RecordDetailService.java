package com.gaenari.backend.domain.recordDetail.service;

import com.gaenari.backend.domain.recordDetail.dto.RecordDetailDto;

public interface RecordDetailService {

    RecordDetailDto getExerciseRecordDetail(String accountId, Long exerciseId);

}
