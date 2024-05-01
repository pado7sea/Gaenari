package com.gaenari.backend.domain.afterExercise.service;

import com.gaenari.backend.domain.afterExercise.dto.requestDto.SaveExerciseRecordDto;

public interface AfterExerciseService {

    Long saveExerciseRecord(Long memberId, SaveExerciseRecordDto exerciseDto);

}
