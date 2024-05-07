package com.gaenari.backend.domain.afterExercise.service;

import com.gaenari.backend.domain.afterExercise.dto.requestDto.SaveExerciseRecordDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;

public interface AfterExerciseService {

    // 누적 통계 업데이트
    TotalStatisticDto updateExerciseStatistics(String memberId, SaveExerciseRecordDto exerciseDto);

    // 프로그램 사용 기록 1 증가
    Integer updateProgramUsageCount(String memberId, SaveExerciseRecordDto exerciseDto);

    // 운동 기록 저장
    Long saveExerciseRecord(String memberId, SaveExerciseRecordDto exerciseDto);

}
